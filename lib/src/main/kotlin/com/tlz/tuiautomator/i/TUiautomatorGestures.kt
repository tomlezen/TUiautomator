package com.tlz.tuiautomator.i

import com.tlz.tuiautomator.TUiautomatorMethods
import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.annotations.TUiautomatorMethodName
import com.tlz.tuiautomator.annotations.TUiautomatorTouchEventType
import com.tlz.tuiautomator.i.handlers.TUiautomatorGesturesHandler
import com.tlz.tuiautomator.utils.newTProxy

/**
 * 手势相关接口.
 * 后续增加百分比点击.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 17:29.
 */
interface TUiautomatorGestures {

    /**
     * 点击.
     * @param x Int
     * @param y Int
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.CLICK)
    fun click(x: Int, y: Int): TUiautomatorResult<Boolean>

    /**
     * 长按.
     * @param x Int
     * @param y Int
     * @param duration Long
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.LONG_CLICK)
    fun longClick(x: Int, y: Int, duration: Long? = null): TUiautomatorResult<Boolean>

    /**
     * 双击.
     * @param x Int
     * @param y Int
     * @param delay Long
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.DOUBLE_CLICK)
    fun doubleClick(x: Int, y: Int, delay: Long = 100): TUiautomatorResult<Boolean>

    /**
     * 滑，扫.
     * @param sx Int
     * @param sy Int
     * @param ex Int
     * @param ey Int
     * @param steps Int 每步为5ms
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.SWIPE)
    fun swipe(sx: Int, sy: Int, ex: Int, ey: Int, steps: Int = 4): TUiautomatorResult<Boolean>

    /**
     * 滑动点到点.
     * 用于9宫格解锁.
     * @param p Pair<Int, Int>
     * @param duration Long 点到点的执行时间
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.SWIPE)
    fun swipe(vararg p: Pair<Int, Int>, duration: Long = 50): TUiautomatorResult<Boolean>

    /**
     * 拖拽.
     * @param sx Int
     * @param sy Int
     * @param ex Int
     * @param ey Int
     * @param duration Long
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.DRAG)
    fun drag(sx: Int, sy: Int, ex: Int, ey: Int, duration: Long = 50): TUiautomatorResult<Boolean>

    /**
     * 按下操作.
     * @param x Int
     * @param y Int
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.INJECT_INPUT_EVENT)
    @TUiautomatorTouchEventType(0)
    fun touchDown(x: Int, y: Int): TUiautomatorResult<Boolean>

    /**
     * 手势移动.
     * @param x Int
     * @param y Int
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.INJECT_INPUT_EVENT)
    @TUiautomatorTouchEventType(2)
    fun touchMove(x: Int, y: Int): TUiautomatorResult<Boolean>

    /**
     * 手势抬起.
     * @param x Int
     * @param y Int
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.INJECT_INPUT_EVENT)
    @TUiautomatorTouchEventType(1)
    fun touchUp(x: Int, y: Int): TUiautomatorResult<Boolean>

    companion object {
        operator fun invoke(service: TUiautomatorService): TUiautomatorGestures =
            newTProxy(TUiautomatorGesturesHandler(service))
    }
}