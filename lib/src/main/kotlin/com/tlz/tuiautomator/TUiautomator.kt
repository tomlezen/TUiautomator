package com.tlz.tuiautomator

import com.tlz.tuiautomator.i.TUiautomatorGestures
import com.tlz.tuiautomator.i.TUiautomatorKeys
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

    /** 按键. */
    val keys: TUiautomatorKeys

    /** 手势. */
    val gestures: TUiautomatorGestures
//
//    /**
//     * 点击坐标.
//     * @param x Int
//     * @param y Int
//     * @return TUiautomatorResult<Boolean>
//     */
//    suspend fun click(x: Int, y: Int): TUiautomatorResult<Boolean>
//
//    /**
//     * 双击坐标.
//     * @param x Int
//     * @param y Int
//     * @param delay Long 点击时间间隔.
//     * @return TUiautomatorResult<Boolean>
//     */
//    suspend fun doubleClick(x: Int, y: Int, delay: Long = 0L): TUiautomatorResult<Boolean>
//
//    /**
//     * 滑动到末尾.
//     * @param scrollable Boolean
//     * @return TUiautomatorResult<Boolean>
//     */
//    suspend fun scrollToEnd(scrollable: Boolean): TUiautomatorResult<Boolean>
//
//    suspend fun scrollTo()
//
//    suspend fun scrollForward()
//
//    suspend fun scrollToBeginning()

//    suspend fun hierarchy(): TUiautomatorResult<Boolean>

    companion object {

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