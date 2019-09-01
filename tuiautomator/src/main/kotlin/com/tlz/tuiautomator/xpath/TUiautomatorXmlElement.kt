package com.tlz.tuiautomator.xpath

import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.utils.TRect

/**
 * Created by Tomlezen.
 * Date: 2019-08-17.
 * Time: 12:38.
 */
interface TUiautomatorXmlElement {

    /** 范围. */
    val rect: TRect

    /**
     * 偏移.
     * @param px Float
     * @param py Float
     * @return Pair<Int, Int>
     */
    fun offset(px: Float = 0f, py: Float = 0f): Pair<Int, Int>

    /** 中心点. */
    val center: Pair<Int, Int>

    /** 获取文字内容. */
    val text: String

    /**
     * 获取属性.
     * @param name String
     * @return String
     */
    fun attribute(name: String): String

    val children: List<TUiautomatorXmlElement>

    suspend fun click(): TUiautomatorResult<Boolean>
}