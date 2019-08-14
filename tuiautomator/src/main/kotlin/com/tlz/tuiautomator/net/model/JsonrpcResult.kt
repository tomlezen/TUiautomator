package com.tlz.tuiautomator.net.model

import com.tlz.tuiautomator.exceptions.TUiautomatorRequestException

/**
 * jsonrpc请求结果.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 14:36.
 */
data class JsonrpcResult(
    val jsonrpc: String,
    val id: String,
    val result: Any? = null,
    val error: Error? = null
) {

    data class Error(
        val code: Int,
        val message: String = "",
        val data: String? = ""
    )

    fun unwrap(): Any {
        if (result != null) return result
        throw TUiautomatorRequestException(
            error?.code ?: -1,
            error?.message ?: "",
            error?.data ?: ""
        )
    }
}