package com.tlz.tuiautomator.i.handlers

import com.tlz.tuiautomator.TUiautomatorMethods
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.exceptions.TUiautomatorParamException
import com.tlz.tuiautomator.net.request.jsonrpcRequest
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.utils.filterT
import com.tlz.tuiautomator.utils.tMethodName
import com.tlz.tuiautomator.utils.toTBool
import com.tlz.tuiautomator.utils.toTLong
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * By tomlezen.
 * Date: 2019-08-13.
 * Time: 17:21.
 */
class TUiautomatorToastHandler(private val service: TUiautomatorService) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any =
        runBlocking {
            runTCatching {
                val methodName = method.tMethodName
                when (methodName) {
                    TUiautomatorMethods.Toast.GET_MESSAGE -> {
                        val waitTimeout = args?.getOrNull(0)?.toTLong()
                            ?: throw TUiautomatorParamException("param waitTimeout is null")
                        val cacheTimeout = args.getOrNull(1)?.toTLong()
                            ?: throw TUiautomatorParamException("param cacheTimeout is null")
                        val endTime = System.currentTimeMillis() + waitTimeout
                        while (System.currentTimeMillis() < endTime) {
                            val message =
                                (service rqNoUnwrap jsonrpcRequest(
                                    method = methodName,
                                    params = arrayOf(cacheTimeout)
                                )).result
                            if (message != null) {
                                return@runTCatching message
                            }
                            delay(500)
                        }
                        return@runTCatching args.getOrNull(2)
                    }
                }
                (service rq jsonrpcRequest(method = methodName, params = args?.filterT())).toTBool()
            }
        }
}