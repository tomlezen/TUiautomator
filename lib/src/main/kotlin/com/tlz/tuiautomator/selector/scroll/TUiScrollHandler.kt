package com.tlz.tuiautomator.selector.scroll

import com.tlz.tuiautomator.TUiautomatorMethods
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.selector.TUiSelector
import com.tlz.tuiautomator.selector.TUiautomatorSelectorAction
import com.tlz.tuiautomator.toTBool
import kotlinx.coroutines.runBlocking
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * Created by Tomlezen.
 * Date: 2019-07-27.
 * Time: 12:08.
 */
class TUiScrollHandler internal constructor(
    private val selectorAction: TUiautomatorSelectorAction
) : InvocationHandler {

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any =
        runBlocking {
            runTCatching {
                // 对scrollTo做特殊处理
                val localArgs = if (method.name == TUiautomatorMethods.SCROLL_TO) {
                    val selector = TUiSelector().apply(args[1] as (TUiSelector.() -> Unit))
                    arrayOf(selector.calculateMask(), args[0])
                } else {
                    args
                }
                selectorAction.submit(method.name, localArgs).toTBool()
            }
        }
}