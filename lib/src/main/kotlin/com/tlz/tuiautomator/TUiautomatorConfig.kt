package com.tlz.tuiautomator

/**
 * 相关配置参数.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 14:00.
 * @property atxAgentIp String atx-agent服务地址（默认为当前手机ip地址）
 * @property waitTimeout Int 全局寻找元素超时时间（秒）
 * @property clickPostDelay Long 点击元素延迟时间（毫秒）
 */
data class TUiautomatorConfig(
    val atxAgentIp: String = TUiautomator.ip,
    val waitTimeout: Int = 20,
    val clickPostDelay: Long = 0L
)