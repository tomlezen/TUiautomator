package com.tlz.tuiautomator.net.model

import com.tlz.tuiautomator.exceptions.TUiautomatorRequestException

/**
 * shell命令请求结果.
 * By tomlezen.
 * Date: 2019-08-14.
 * Time: 14:33.
 */
data class ShellCmdResult(
    val error: Any? = "",
    val exitCode: Int,
    val output: String? = ""
) {

    fun unwrap(): String {
        if (exitCode == 0) return output ?: ""
        throw TUiautomatorRequestException(
            exitCode,
            error.toString(),
            output ?: ""
        )
    }

}