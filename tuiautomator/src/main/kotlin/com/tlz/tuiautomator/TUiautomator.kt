package com.tlz.tuiautomator

import com.tlz.tuiautomator.i.*
import com.tlz.tuiautomator.selector.TUiSelector
import com.tlz.tuiautomator.selector.TUiautomatorSelectors
import com.tlz.tuiautomator.step.TUiautomatorStep
import com.tlz.tuiautomator.step.TUiautomatorStepsTask
import com.tlz.tuiautomator.xpath.TUiautomatorXpath
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

    /**
     * ui选择器.
     * @param selector [@kotlin.ExtensionFunctionType] Function1<TUiSelector, Unit>
     * @return TUiautomatorSelectors
     */
    fun selector(selector: TUiSelector.() -> Unit): TUiautomatorSelectors

    /**
     * xpath选择.
     * @param xpath String
     * @return TUiautomatorXpath
     */
    fun xpath(xpath: String): TUiautomatorXpath

    /**
     * 创建一个任务.
     * @param isTestMode Boolean 是否是测试模式. 当为测试模式时非异步运行.
     * @return TUiautomatorStepsTask
     */
    fun createTask(isTestMode: Boolean = false): TUiautomatorStepsTask

    /**
     * 创建一个步骤.
     * @param step SuspendFunction1<[@kotlin.ParameterName] TUiautomatorStepsTask, Int?> 返回下一个需要执行的步骤id.
     * @return TUiautomatorStep
     */
    fun createStep(step: suspend (task: TUiautomatorStepsTask) -> Int?): TUiautomatorStep

    companion object {

        /** atx-agent默认端口. */
        const val ATX_AGENT_PORT = "7912"

        /** 受保护的app. */
        val PROTECT_APPS = arrayOf("com.github.uiautomator", "com.github.uiautomator.test")

        /** 包名匹配Regex. */
        val PKG_NAME_REGEX by lazy { "^([a-zA-Z]+[.][a-zA-Z]+)[.]*.*".toRegex(RegexOption.DOT_MATCHES_ALL) }

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