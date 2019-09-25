package com.tlz.tuiautomator.selector.model

/**
 * ui信息.
 * Created by Tomlezen.
 * Date: 2019-07-28.
 * Time: 14:58.
 */
data class TUiInfo(
    val bounds: Bounds,
    val childCount: Int,
    val className: String?,
    val contentDescription: String?,
    val packageName: String?,
    val resourceName: String?,
    val text: String?,
    val visibleBounds: Bounds?,
    val checkable: Boolean,
    val checked: Boolean,
    val clickable: Boolean,
    val enabled: Boolean,
    val focusable: Boolean,
    val focused: Boolean,
    val longClickable: Boolean,
    val scrollable: Boolean,
    val selected: Boolean
) {

    data class Bounds(
        val bottom: Int,
        val left: Int,
        val right: Int,
        val top: Int
    ) {

        val centerX: Int
            get() = (left + right) / 2

        val centerY: Int
            get() = (top + bottom) / 2

    }

}