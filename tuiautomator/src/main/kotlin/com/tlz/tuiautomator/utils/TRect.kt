package com.tlz.tuiautomator.utils

/**
 * Created by Tomlezen.
 * Date: 2019-07-29.
 * Time: 20:07.
 */
data class TRect(var left: Int, var top: Int, var right: Int, var bottom: Int) {

    val width: Int
        get() = right - left

    val height: Int
        get() = bottom - top

    fun center() = (left + right) / 2 to (top + bottom) / 2
}