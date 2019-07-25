package com.tlz.tuiautomator

import java.lang.RuntimeException

/**
 * 数据请求异常.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 16:09.
 */
class TUiautomatorRequestException(
    val code: Int,
    message: String,
    val data: String
) : RuntimeException(message)