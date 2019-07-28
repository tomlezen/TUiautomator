package com.tlz.tuiautomator.selector.fling

import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.newTProxy
import com.tlz.tuiautomator.selector.TUiautomatorSelectorAction
import com.tlz.tuiautomator.selector.scroll.TUiScrollHandler

/**
 * 快读滑动.
 * Created by Tomlezen.
 * Date: 2019-07-27.
 * Time: 13:17.
 */
interface TUiFling {

    /**
     * 快速滑动到开始.
     * @param vertical Boolean
     * @param maxSwipe Int
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun flingToBeginning(
        vertical: Boolean = true,
        maxSwipe: Int = 500
    ): TUiautomatorResult<Boolean>

    /**
     * 快速滑动到结束.
     * @param vertical Boolean
     * @param maxSwipe Int
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun flingToEnd(vertical: Boolean = true, maxSwipe: Int = 500): TUiautomatorResult<Boolean>

    /**
     * 快速向前滑动.
     * @param vertical Boolean
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun flingForward(vertical: Boolean = true): TUiautomatorResult<Boolean>

    /**
     * 快速向后滑动.
     * @param vertical Boolean
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun flingBackward(vertical: Boolean = true): TUiautomatorResult<Boolean>

    companion object {
        operator fun invoke(selectorAction: TUiautomatorSelectorAction): TUiFling =
            newTProxy(TUiScrollHandler(selectorAction))
    }

}