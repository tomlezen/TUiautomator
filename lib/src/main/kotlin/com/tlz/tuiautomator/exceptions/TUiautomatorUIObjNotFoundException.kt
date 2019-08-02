package com.tlz.tuiautomator.exceptions

import java.lang.RuntimeException

/**
 * UI没有找到异常.
 * By tomlezen.
 * Date: 2019-08-02.
 * Time: 15:00.
 */
class TUiautomatorUIObjNotFoundException(val method: String) : RuntimeException() {

    override fun getLocalizedMessage(): String? = "method = $method"

}