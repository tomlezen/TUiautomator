package com.tlz.tuiautomator.selector.scroll

import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.newTProxy
import com.tlz.tuiautomator.selector.TUiSelector
import com.tlz.tuiautomator.selector.TUiautomatorSelectorAction

/**
 * 滑动操作.
 * Created by Tomlezen.
 * Date: 2019-07-27.
 * Time: 11:57.
 */
interface TUiScroll {

    /**
     * 滑动到开始.
     * @param vertical Boolean
     * @param maxSwipe Int
     * @param steps Int
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun scrollToBeginning(
        vertical: Boolean = true,
        maxSwipe: Int = 500,
        steps: Int = 20
    ): TUiautomatorResult<Boolean>

    /**
     * 滑动到结束.
     * @param vertical Boolean
     * @param maxSwipe Int
     * @param steps Int
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun scrollToEnd(vertical: Boolean = true, maxSwipe: Int = 500, steps: Int = 20): TUiautomatorResult<Boolean>

    /**
     * 滑动到指定条件位置.
     * @param vertical Boolean
     * @param selector [@kotlin.ExtensionFunctionType] Function1<TUiSelector, Unit>
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun scrollTo(vertical: Boolean = true, selector: TUiSelector.() -> Unit): TUiautomatorResult<Boolean>

    /**
     * 向前滑动.
     * @param vertical Boolean
     * @param steps Int
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun scrollForward(vertical: Boolean = true, steps: Int = 20): TUiautomatorResult<Boolean>

    /**
     * 向后滑动.
     * @param vertical Boolean
     * @param steps Int
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun scrollBackward(vertical: Boolean = true, steps: Int = 20): TUiautomatorResult<Boolean>

    companion object {
        operator fun invoke(selectorAction: TUiautomatorSelectorAction): TUiScroll =
            newTProxy(TUiScrollHandler(selectorAction))
    }
}