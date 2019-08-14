package com.tlz.tuiautomator.annotations

/**
 * 触摸事件类型.
 * By tomlezen.
 * Date: 2019-07-26.
 * Time: 16:51.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TUiautomatorTouchEventType(val type: Int)