package com.tlz.tuiautomator.net.model

/**
 * Session请求结果.
 * By tomlezen.
 * Date: 2019-08-14.
 * Time: 15:08.
 */
data class SessionResult(
    val mainActivity: String?,
    val output: String?,
    val success: Boolean
)