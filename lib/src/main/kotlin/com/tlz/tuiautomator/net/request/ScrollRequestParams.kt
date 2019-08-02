package com.tlz.tuiautomator.net.request

/**
 * 滑动请求参数.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 15:34.
 */
data class ScrollRequestParams(
    val mask: Int,
    val childOrSibling: Array<Any> = arrayOf(),
    val childOrSiblingSelector: Array<Any> = arrayOf(),
    val scrollable: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ScrollRequestParams

        if (mask != other.mask) return false
        if (!childOrSibling.contentEquals(other.childOrSibling)) return false
        if (!childOrSiblingSelector.contentEquals(other.childOrSiblingSelector)) return false
        if (scrollable != other.scrollable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mask
        result = 31 * result + childOrSibling.contentHashCode()
        result = 31 * result + childOrSiblingSelector.contentHashCode()
        result = 31 * result + scrollable.hashCode()
        return result
    }
}