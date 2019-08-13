package com.tlz.tuiautomator.i.handlers

import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.net.request.pressKeyReuest
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.utils.tMethodName
import com.tlz.tuiautomator.utils.toTBool
import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * By tomlezen.
 * Date: 2019-07-26.
 * Time: 10:44.
 */
class TUiautomatorKeysHandler(private val service: TUiautomatorService) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any =
        runBlocking {
            runTCatching {
                (service rq pressKeyReuest(method.tMethodName)).toTBool()
            }
        }
}