package com.tlz.tuiautomator.i.handlers

import com.tlz.tuiautomator.*
import com.tlz.tuiautomator.annotations.TUiautomatorMethodName
import com.tlz.tuiautomator.annotations.TUiautomatorTouchEventType
import com.tlz.tuiautomator.exceptions.TUiautomatorParamException
import com.tlz.tuiautomator.i.TUiautomatorGestures
import com.tlz.tuiautomator.net.request.jsonrpcRequest
import com.tlz.tuiautomator.utils.toTBool
import com.tlz.tuiautomator.utils.toTInt
import com.tlz.tuiautomator.utils.toTLong
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * By tomlezen.
 * Date: 2019-07-26.
 * Time: 11:28.
 */
class TUiautomatorGesturesHandler(private val service: TUiautomatorService) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any?>): Any =
        runBlocking {
            runTCatching {
                // 先查找方法名注解
                val methodNameAnnotation = method.getAnnotation(TUiautomatorMethodName::class.java)
                    ?: throw TUiautomatorParamException("method lack TUiautomatorMethodName annotation")
                val methodName = methodNameAnnotation.name
                // 这里为了方便追加参数
                // 需要处理下 vararg参数 以及 Pair
                val params = mutableListOf<Any?>()
                args.forEach {
                    if (it is Array<*>) {
                        it.forEach { sub ->
                            if (sub is Pair<*, *>) {
                                params.add(sub.first!!)
                                params.add(sub.second!!)
                            } else {
                                params.add(sub!!)
                            }
                        }
                    } else {
                        params.add(it)
                    }
                }
                when (methodName) {
                    TUiautomatorMethods.INJECT_INPUT_EVENT -> {
                        params.add(
                            0, (method.getAnnotation(TUiautomatorTouchEventType::class.java)
                                ?: throw TUiautomatorParamException("method lack TUiautomatorTouchEventType annotation")).type
                        )
                        // 这个参数目前不知代表什么意思
                        params.add(0)
                    }
                    TUiautomatorMethods.LONG_CLICK -> {
                        val x = params[0].toTInt()
                        val y = params[1].toTInt()
                        // 先发送down事件
                        (proxy as TUiautomatorGestures).touchDown(x, y).throwOnFailure()
                        delay(params[2]?.toTLong() ?: service.config.longClickDuration)
                        // 在发送up事件
                        return@runTCatching proxy.touchUp(x, y).toTBool()
                    }
                    TUiautomatorMethods.DOUBLE_CLICK -> {
                        val x = params[0].toTInt()
                        val y = params[1].toTInt()
                        // 先发送down事件
                        (proxy as TUiautomatorGestures).touchDown(x, y).throwOnFailure()
                        delay(params[2].toTLong())
                        // 在发送up事件
                        proxy.touchUp(x, y).throwOnFailure()
                        // 发送点击事件
                        return@runTCatching proxy.click(x, y).toTBool()
                    }
                    TUiautomatorMethods.CLICK -> delay(service.config.clickPostDelay)
                    else -> {

                    }
                }
                (service rq jsonrpcRequest(method = methodName, params = params.toTypedArray())).toTBool()
            }
        }
}