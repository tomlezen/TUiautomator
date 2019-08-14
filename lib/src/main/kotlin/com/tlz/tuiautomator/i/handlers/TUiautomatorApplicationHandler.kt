package com.tlz.tuiautomator.i.handlers

import com.tlz.tuiautomator.TUiautomator
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.annotations.TUiautomatorFormatParam
import com.tlz.tuiautomator.annotations.TUiautomatorShellCmd
import com.tlz.tuiautomator.exceptions.TUiautomatorParamException
import com.tlz.tuiautomator.i.TUiautomatorApplication
import com.tlz.tuiautomator.onSuccess
import com.tlz.tuiautomator.runTCatching
import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import kotlin.reflect.full.findAnnotation

/**
 * By tomlezen.
 * Date: 2019-08-14.
 * Time: 15:31.
 */
class TUiautomatorApplicationHandler(private val service: TUiautomatorService) : InvocationHandler {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any? =
        runBlocking {
            runTCatching {
                when (method.name) {
                    TUiautomatorApplication::start.name -> {
                        service.apiService.session(args?.getOrNull(0).toString(), "-W")
                    }
                    TUiautomatorApplication::stopAll.name -> {
                        var killAppCount = 0
                        val protectApps =
                            (TUiautomator.PROTECT_APPS + service.config.selfAppPkgName) + ((args?.get(0) as? Array<String>)
                                ?: emptyArray())
                        val processApps = "([^s]+)$".toRegex().findAll(service.shell.execute("ps").getOrThrow()).map { it.value }.toList()
                        service.shell.execute("pm list packages -3")
                            .getOrThrow()
                            .split("\n")
                            .asSequence()
                            .map { it.replace("package:", "") }
                            .filter { it in processApps }
                            .filter { it !in protectApps }
                            .forEach {
                                service.application.stop(it).onSuccess {
                                    killAppCount++
                                }
                            }
                        killAppCount
                    }
                    else -> {
                        // 获取需要格式化的参数
                        val fArgs = mutableListOf<Any?>()
                        method.parameters.forEachIndexed { index, parameter ->
                            if (parameter.getAnnotation(TUiautomatorFormatParam::class.java) != null) {
                                fArgs += args?.getOrNull(index)
                            }
                        }
                        // 获取cmd命令
                        val cmd = method.getAnnotation(TUiautomatorShellCmd::class.java)?.cmd
                            ?: throw TUiautomatorParamException("cmd is null")
                        service.shell.execute(String.format(cmd, *(fArgs.toTypedArray()))).getOrThrow()
                    }
                }
            }
        }
}