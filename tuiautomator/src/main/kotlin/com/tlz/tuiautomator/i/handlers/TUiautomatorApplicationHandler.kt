package com.tlz.tuiautomator.i.handlers

import com.tlz.tuiautomator.TUiautomator
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.annotations.TUiautomatorFormatParam
import com.tlz.tuiautomator.annotations.TUiautomatorShellCmd
import com.tlz.tuiautomator.exceptions.TUiautomatorParamException
import com.tlz.tuiautomator.i.TUiautomatorApplication
import com.tlz.tuiautomator.onSuccess
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.utils.toTLong
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.regex.Pattern

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
                    TUiautomatorApplication::currentApp.name -> {
                        val result = service.shell.execute("dumpsys window windows").getOrThrow()
                        CURRENT_APP_REGEX.find(result).let {
                            it?.groupValues?.get(1)!! to it.groupValues[2]
                        }
                    }
                    TUiautomatorApplication::waitActivity.name -> {
                        val needWaitActivity = args?.getOrNull(0).toString()
                        val timeoutTime = System.currentTimeMillis() + (args?.getOrNull(1).toTLong(10_000))
                        var result = false
                        while (System.currentTimeMillis() < timeoutTime) {
                            if (service.application.currentApp().getOrThrow().second == needWaitActivity) {
                                result = true
                                break
                            }
                            delay(1_000)
                        }
                        result
                    }
                    TUiautomatorApplication::start.name -> {
                        service.apiService.session(args?.getOrNull(0).toString(), "-W")
                    }
                    TUiautomatorApplication::stopAll.name -> {
                        var killAppCount = 0
                        val protectApps =
                            (TUiautomator.PROTECT_APPS + service.config.selfAppPkgName) + ((args?.get(0) as? Array<String>)
                                ?: emptyArray())
                        val processApps = PROCESS_APP_REGEX
                            .findAll(service.shell.execute("ps").getOrThrow())
                            .map { it.value }
                            .filter { it.contains(TUiautomator.PKG_NAME_REGEX) }
                            .toList()
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
                    TUiautomatorApplication::info.name -> service.apiService.appInfo(args?.get(0).toString())
                    else -> {
                        // 获取需要格式化的参数
                        val fArgs = mutableListOf<Any?>()
                        method.parameterAnnotations.forEachIndexed { index, annotations ->
                            if (annotations.isNotEmpty() && annotations[0] is TUiautomatorFormatParam) {
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

    companion object {
        val CURRENT_APP_REGEX by lazy { "mCurrentFocus=Window\\{.*\\s+([^\\s]+)/([^\\s]+)}".toRegex() }

        val PROCESS_APP_REGEX by lazy {
            "([^\\s]+)$".toRegex(
                setOf(
                    RegexOption.MULTILINE,
                    RegexOption.DOT_MATCHES_ALL
                )
            )
        }
    }
}