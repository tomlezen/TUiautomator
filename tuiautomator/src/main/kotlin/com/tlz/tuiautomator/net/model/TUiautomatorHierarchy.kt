package com.tlz.tuiautomator.net.model

import com.tlz.tuiautomator.utils.TRect

/**
 * xxx.
 * By tomlezen.
 * Date: 2019-08-16.
 * Time: 11:03.
 */
data class TUiautomatorHierarchy(
    val nodes: List<Node> = listOf()
) {

    data class Node(
        val index: Int,
        val text: String,
        val resourceId: String,
        val className: String,
        val pkgName: String,
        val contentDesc: String,
        val checkable: Boolean,
        val checked: Boolean,
        val clickable: Boolean,
        val enabled: Boolean,
        val focusable: Boolean,
        val focused: Boolean,
        val scrollable: Boolean,
        val longClickable: Boolean,
        val password: Boolean,
        val selected: Boolean,
        val visibleToUser: Boolean,
        val bounds: TRect,
        val children: List<Node>
    )

}