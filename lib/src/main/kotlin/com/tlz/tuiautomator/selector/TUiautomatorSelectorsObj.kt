package com.tlz.tuiautomator.selector

import com.google.gson.internal.LinkedTreeMap
import com.tlz.tuiautomator.*
import com.tlz.tuiautomator.net.request.jsonrpcRequest
import com.tlz.tuiautomator.selector.fling.TUiFling
import com.tlz.tuiautomator.selector.model.TUiInfo
import com.tlz.tuiautomator.selector.scroll.TUiScroll
import kotlin.coroutines.Continuation

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