package com.tlz.tuiautomator.i

import com.tlz.tuiautomator.TUiautomatorMethods
import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.annotations.TUiautomatorMethodName

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
    @TUiautomatorMethodName(TUiautomatorMethods.CLICK)
    fun longClick(x: Int, y: Int, duration: Long = 100): TUiautomatorResult<Boolean>

    /**
     * 双击.
     * @param x Int
     * @param y Int
     * @param delay Long
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.CLICK)
    fun doubleClick(x: Int, y: Int, delay: Long = 500): TUiautomatorResult<Boolean>

    /**
     * 滑，扫.
     * @param sx Int
     * @param sy Int
     * @param ex Int
     * @param ey Int
     * @param duration Long
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.SWIPE)
    fun swipe(sx: Int, sy: Int, ex: Int, ey: Int, duration: Long = 500): TUiautomatorResult<Boolean>

    /**
     * 滑动点到点.
     * 用于9宫格解锁.
     * @param p0 Pair<Int, Int>
     * @param p1 Pair<Int, Int>
     * @param p2 Pair<Int, Int>
     * @param speed Long
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.SWIPE)
    fun swipe(p0: Pair<Int, Int>, p1: Pair<Int, Int>, p2: Pair<Int, Int>, speed: Long): TUiautomatorResult<Boolean>

    /**
     * 拖拽.
     * @param sx Int
     * @param sy Int
     * @param ex Int
     * @param ey Int
     * @param duration Long
     * @return TUiautomatorResult<Boolean>
     */
    fun drag(sx: Int, sy: Int, ex: Int, ey: Int, duration: Long = 500): TUiautomatorResult<Boolean>

    /**
     * 按下操作.
     * @param x Int
     * @param y Int
     * @return TUiautomatorResult<Boolean>
     */
    fun touchDown(x: Int, y: Int): TUiautomatorResult<Boolean>

    /**
     * 手势移动.
     * @param x Int
     * @param y Int
     * @return TUiautomatorResult<Boolean>
     */
    fun touchMove(x: Int, y: Int): TUiautomatorResult<Boolean>

    /**
     * 手势抬起.
     * @param x Int
     * @param y Int
     * @return TUiautomatorResult<Boolean>
     */
    fun touchUp(x: Int, y: Int): TUiautomatorResult<Boolean>
}