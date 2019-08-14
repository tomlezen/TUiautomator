package com.tlz.tuiautomator.i.handlers

import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.i.TUiautomatorShell
import com.tlz.tuiautomator.runTCatching
import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * By tomlezen.
 * Date: 2019-08-14.
 * Time: 14:18.
 */
class TUiautomatorShellHandler(private val service: TUiautomatorService) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? =
        runBlocking {
            runTCatching {
                when (method.name) {
                    TUiautomatorShell::execute.name -> {
                        service.apiService.shell(
                            args?.get(0).toString().replace(" ", "+"),
                            service.tools safetyTimeout (args?.get(1).toString().toLong())
                        ).unwrap()
                    }
                    else -> null
                }
            }
        }
}