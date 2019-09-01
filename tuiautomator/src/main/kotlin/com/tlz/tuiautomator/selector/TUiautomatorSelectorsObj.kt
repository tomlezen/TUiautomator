package com.tlz.tuiautomator.selector

import com.google.gson.internal.LinkedTreeMap
import com.tlz.tuiautomator.TUiautomatorMethods
import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.exceptions.TUiautomatorUIObjNotFoundException
import com.tlz.tuiautomator.net.request.jsonrpcRequest
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.selector.fling.TUiFling
import com.tlz.tuiautomator.selector.model.TUiInfo
import com.tlz.tuiautomator.selector.scroll.TUiScroll
import com.tlz.tuiautomator.utils.*
import kotlin.math.roundToInt

/**
 * Created by Tomlezen.
 * Date: 2019-07-27.
 * Time: 12:07.
 */
class TUiautomatorSelectorsObj(
    private val service: TUiautomatorService,
    private val selector: TUiSelector
) : TUiautomatorSelectors, TUiautomatorSelectorAction {

    override val scroll: TUiScroll = TUiScroll(this)

    override val fling: TUiFling = TUiFling(this)

    /**
     * 等待ui出现或者消失.
     * @param exists Boolean
     * @param timeout Int? 等待时间
     * @return TUiautomatorResult<Boolean>
     */
    private suspend fun wait(exists: Boolean = true, timeout: Int? = null): TUiautomatorResult<Boolean> =
        runTCatching {
            if (exists) {
                submit(TUiautomatorMethods.WAIT_FOR_EXISTS, arrayOf((timeout ?: service.config.waitTimeout) * 1000))
            } else {
                submit(TUiautomatorMethods.WAIT_UNTIL_GONE, arrayOf((timeout ?: service.config.waitTimeout) * 1000))
            }.toTBool()
        }

    /**
     * 必须等待到ui出现.
     * @param exists Boolean
     * @param timeout Int?
     */
    private suspend fun mustWait(exists: Boolean = true, timeout: Int? = null) {
        if (!wait(exists, timeout).getOrThrow()) {
            throw TUiautomatorUIObjNotFoundException("wait")
        }
    }

    override suspend fun exists(): TUiautomatorResult<Boolean> =
        runTCatching {
            submit(TUiautomatorMethods.EXIST, null).toTBool()
        }

    override suspend fun info(): TUiautomatorResult<TUiInfo> =
        runTCatching {
            (submit(TUiautomatorMethods.OBJ_INFO, null) as LinkedTreeMap<*, *>).toJson().toObj<TUiInfo>()
        }

    override suspend fun bounds(): TUiautomatorResult<TRect> =
        runTCatching {
            // 先获取ui信息
            val info = info().getOrThrow()
            val bounds = info.bounds
            TRect(bounds.left, bounds.top, bounds.right, bounds.bottom)
        }

    override suspend fun center(xOffset: Float, yOffset: Float): TUiautomatorResult<Pair<Int, Int>> =
        runTCatching {
            val bounds = bounds().getOrThrow()
            (bounds.left + (bounds.width * xOffset).roundToInt()) to (bounds.top + (bounds.height * yOffset).roundToInt())
        }

    override suspend fun click(xOffset: Float, yOffset: Float): TUiautomatorResult<Boolean> =
        runTCatching {
            val (x, y) = center(xOffset, yOffset).getOrThrow()
            service.gestures.click(x, y).getOrThrow()
        }

    override suspend fun longClick(duration: Long?, timeout: Int?): TUiautomatorResult<Boolean> =
        runTCatching {
            mustWait(timeout = timeout)
            val (x, y) = center().getOrThrow()
            service.gestures.longClick(x, y, duration).getOrThrow()
        }

    override suspend fun swipe(direction: TUiautomatorSwipeDirection, steps: Int): TUiautomatorResult<Boolean> =
        runTCatching {
            mustWait()
            val bounds = info().getOrThrow().let { it.visibleBounds ?: it.bounds }
            when (direction) {
                TUiautomatorSwipeDirection.LEFT ->
                    service.gestures.swipe(
                        bounds.centerX,
                        bounds.centerY,
                        bounds.left + 1,
                        bounds.centerY,
                        steps
                    )
                TUiautomatorSwipeDirection.UP ->
                    service.gestures.swipe(
                        bounds.centerX,
                        bounds.centerY,
                        bounds.centerX,
                        bounds.top + 1,
                        steps
                    )
                TUiautomatorSwipeDirection.RIGHT ->
                    service.gestures.swipe(
                        bounds.centerX,
                        bounds.centerY,
                        bounds.right - 1,
                        bounds.centerY,
                        steps
                    )
                TUiautomatorSwipeDirection.DOWN ->
                    service.gestures.swipe(
                        bounds.centerX,
                        bounds.centerY,
                        bounds.centerX,
                        bounds.bottom - 1,
                        steps
                    )
            }.getOrThrow()
        }

    override suspend fun dragTo(x: Float, y: Float): TUiautomatorResult<Boolean> =
        runTCatching {
            mustWait()
            val (rX, rY) = service.tools.percXy2RelXy(x, y)
            submit(TUiautomatorSelectors::dragTo.name, arrayOf(rX, rY, 100)).toTBool()
        }

    override suspend fun submit(methodName: String, args: Array<out Any>?): Any =
        // 这里比较坑 args会被加上Continuation 所以需要过滤掉
        service rq (jsonrpcRequest(
            method = methodName,
            params = (args ?: emptyArray()).toMutableList().apply {
                add(
                    0,
                    selector.calculateMask()
                )
            }.filterT()
        ))
}