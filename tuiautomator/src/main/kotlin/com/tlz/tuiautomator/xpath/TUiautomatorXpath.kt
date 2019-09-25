package com.tlz.tuiautomator.xpath

import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.utils.TRect

/**
 * Xpath.
 * Created by Tomlezen.
 * Date: 2019-08-16.
 * Time: 22:25.
 */
interface TUiautomatorXpath {
    /**
     * 提取所有匹配.
     * @return TUiautomatorResult<List<TUiautomatorXmlElement>>
     */
    suspend fun all(): TUiautomatorResult<List<TUiautomatorXmlElement>>

    /**
     * 点击.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun click(): TUiautomatorResult<Boolean>

    suspend fun text(): TUiautomatorResult<String?>

    suspend fun bounds(): TUiautomatorResult<TRect>

    suspend fun center(xOffset: Float = .5f, yOffset: Float = .5f): TUiautomatorResult<Pair<Int, Int>>
}