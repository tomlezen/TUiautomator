package com.tlz.tuiautomator

import com.tlz.tuiautomator.i.*
import com.tlz.tuiautomator.selector.TUiautomatorSelectors
import com.tlz.tuiautomator.selector.TUiSelector
import java.net.Inet4Address
import java.net.NetworkInterface

/**
 * 创建TUiautomator实例: TUiautomator(TUiautomatorConfig)
 * 代码必须运行在协程作用域下.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 13:57.
 */
interface TUiautomator {

    /** 设备. */
    val device: TUiautomatorDevice

    /** Toast. */
    val toast: TUiautomatorToast

    /** 按键. */
    val keys: TUiautomatorKeys

    /** 手势. */
    val gestures: TUiautomatorGestures

    /** shell命令. */
    val shell: TUiautomatorShell

    /** 应用. */
    val application: TUiautomatorApplication

    /** ui选择器. */
    fun selector(selector: TUiSelector.() -> Unit): TUiautomatorSelectors

    companion object {

        /** atx-agent默认端口. */
        const val ATX_AGENT_PORT = "7912"

        operator fun invoke(config: TUiautomatorConfig = TUiautomatorConfig()):
                TUiautomator = TUiautomatorService(config)

        /** 手机ip地址. */
        internal val ip: String
            get() {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val enumIp = en.nextElement().inetAddresses
                    while (enumIp.hasMoreElements()) {
                        val inetAddress = enumIp.nextElement()
                        if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                            return inetAddress.getHostAddress().toString()
                        }
                    }
                }
                error("没有获取到手机ip地址")
            }
    }

}