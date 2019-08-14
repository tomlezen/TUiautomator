package com.tlz.tuiautomator

/**
 * 相关配置参数.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 14:00.
 * @property atxAgentIp String atx-agent服务地址（默认为当前手机ip地址）
 * @property atxAgentPort String atx-agent服务地址端口
 * @property httpTimeout Int 网络请求超时时间（秒）
 * @property waitTimeout Int 全局寻找元素超时时间（秒），注意不能超过httpTimeout
 * @property longClickDuration Long 长按时down和up间隔时间（毫秒）
 * @property clickPostDelay Long 点击元素延迟时间（毫秒）
 * @property selfAppPkgName String 自己应用包名，用于方调用stopAll方法时，保护此包名的app
 */
data class TUiautomatorConfig(
    val atxAgentIp: String = TUiautomator.ip,
    val atxAgentPort: String = TUiautomator.ATX_AGENT_PORT,
    val httpTimeout: Int = 30,
    val waitTimeout: Int = 20,
    val longClickDuration: Long = 500,
    val clickPostDelay: Long = 0L,
    val selfAppPkgName: String = "****"
)