package com.tlz.tuiautomator.annotations

import kotlin.annotation.Target
import kotlin.annotation.MustBeDocumented
import kotlin.annotation.Retention

/**
 * 按键名注解.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 17:13.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TUiautomatorKeyName(val name: String)