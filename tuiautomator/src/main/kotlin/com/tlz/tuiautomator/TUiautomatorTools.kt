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

    private val windowSize = IntArray(2)

    /**
     * 百分比x，y转真实x, y
     * @return Pair<Int, Int>
     */
    suspend fun percXy2RelXy(x: Float, y: Float): Pair<Int, Int> {
        val rX = min(1f, max(0f, x))
        val rY = min(1f, max(0f, y))
        if (windowSize[0] == 0 || windowSize[1] == 0) {
            service.device.windowSize().getOrThrow().let {
                windowSize[0] = it.first
                windowSize[1] = it.second
            }
        }
        return (windowSize[0] * rX).toInt() to (windowSize[1] * rY).toInt()
    }

    /**
     * 处理超时时间.
     * @param timeout Long
     * @return Long
     */
    infix fun safetyTimeout(timeout: Long) = min(service.config.httpTimeout * 1000L, timeout)
}