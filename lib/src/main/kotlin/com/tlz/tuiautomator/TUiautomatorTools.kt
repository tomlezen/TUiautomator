package com.tlz.tuiautomator

import kotlin.math.max
import kotlin.math.min

/**
 * xxx.
 * By tomlezen.
 * Date: 2019-08-02.
 * Time: 16:14.
 */
class TUiautomatorTools(private val service: TUiautomatorService) {

    /**
     * 百分比x，y转真实x, y
     * @return Pair<Int, Int>
     */
    suspend fun percXy2RelXy(x: Float, y: Float): Pair<Int, Int> {
        val rX = min(1f, max(0f, x))
        val rY = min(1f, max(0f, y))
        return service.device.windowSize().getOrThrow().let {
            (it.first * rX).toInt() to (it.second * rY).toInt()
        }
    }

}