package com.tlz.tuiautomator.net.model

/**
 * 应用信息.
 * By tomlezen.
 * Date: 2019-08-15.
 * Time: 11:01.
 */
data class AppInfoResult(
    val description: String? = "",
    val data: Info? = null,
    val success: Boolean
) {

    data class Info(
        val mainActivity: String,
        val label: String,
        val versionName: String,
        val versionCode: String,
        val size: String
    )

}