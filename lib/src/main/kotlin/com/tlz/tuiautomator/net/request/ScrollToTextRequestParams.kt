package com.tlz.tuiautomator.net.request

import androidx.annotation.Keep

/**
 * 滑动到指定文字请求参数.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 15:44.
 */
@Keep
data class ScrollToTextRequestParams(
    val mask: Int,
    val childOrSibling: Array<Any> = arrayOf(),
    val childOrSiblingSelector: Array<Any> = arrayOf(),
    val text: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScrollToTextRequestParams

        if (mask != other.mask) return false
        if (!childOrSibling.contentEquals(other.childOrSibling)) return false
        if (!childOrSiblingSelector.contentEquals(other.childOrSiblingSelector)) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mask
        result = 31 * result + childOrSibling.contentHashCode()
        result = 31 * result + childOrSiblingSelector.contentHashCode()
        result = 31 * result + text.hashCode()
        return result
    }
}