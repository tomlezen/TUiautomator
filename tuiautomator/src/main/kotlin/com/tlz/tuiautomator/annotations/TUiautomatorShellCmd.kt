package com.tlz.tuiautomator.annotations

/**
 * Shell 命令注解.
 * By tomlezen.
 * Date: 2019-08-14.
 * Time: 14:58.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TUiautomatorShellCmd(val cmd: String)