package com.tlz.tuiautomator.exceptions

import java.lang.RuntimeException

/**
 * 数据请求异常.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 16:09.
 */
class TUiautomatorRequestException(
    val code: Int,
    val message1: String,
    val data: String
) : RuntimeException(message1) {

    override val message: String?
        get() = """
            code = $code,
            message = $message1,
            data = $data,
        """.trimIndent()

}