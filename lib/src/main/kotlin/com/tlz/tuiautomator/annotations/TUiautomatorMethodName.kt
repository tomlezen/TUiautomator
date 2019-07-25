package com.tlz.tuiautomator.annotations

/**
 * 方法名注解.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 17:57.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TUiautomatorMethodName(val name: String)