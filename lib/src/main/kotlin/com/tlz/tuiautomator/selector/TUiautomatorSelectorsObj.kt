package com.tlz.tuiautomator.selector

import com.google.gson.internal.LinkedTreeMap
import com.tlz.tuiautomator.*
import com.tlz.tuiautomator.net.request.jsonrpcRequest
import com.tlz.tuiautomator.selector.fling.TUiFling
import com.tlz.tuiautomator.selector.model.TUiInfo
import com.tlz.tuiautomator.selector.scroll.TUiScroll
import com.tlz.tuiautomator.utils.TRect
import kotlin.coroutines.Continuation
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

    override suspend fun submit(methodName: String, args: Array<out Any>?): Any =
        // 这里比较坑 args会被加上Continuation 所以需要过滤掉
        service rq (jsonrpcRequest(
            method = methodName,
            params = (args ?: emptyArray()).toMutableList().apply {
                add(
                    0,
                    selector.calculateMask()
                )
            }.filter { it !is Continuation<*> }))
}