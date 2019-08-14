package com.tlz.tuiautomator.i.handlers

import com.google.gson.internal.LinkedTreeMap
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.i.TUiautomatorDevice
import com.tlz.tuiautomator.net.model.TDeviceInfo
import com.tlz.tuiautomator.net.request.jsonrpcRequest
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.utils.tMethodName
import com.tlz.tuiautomator.utils.toJson
import com.tlz.tuiautomator.utils.toObj
import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * Created by Tomlezen.
 * Date: 2019-08-02.
 * Time: 20:17.
 */
class TUiautomatorDeviceHandler(private val service: TUiautomatorService) : InvocationHandler {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun invoke(proxy: Any?, method: Method, params: Array<out Any?>?): Any? =
        runBlocking {
            val methodName = method.tMethodName
            runTCatching {
                when (method.name) {
                    TUiautomatorDevice::info.name -> {
                        ((service rq jsonrpcRequest(method = methodName)) as LinkedTreeMap<*, *>).toJson()
                            .toObj<TDeviceInfo>()
                    }
                    TUiautomatorDevice::windowSize.name -> {
                        service.device.info().getOrThrow().let {
                            it.displayWidth to it.displayHeight
                        }
                    }
                    else -> null
                }
            }
        }
}