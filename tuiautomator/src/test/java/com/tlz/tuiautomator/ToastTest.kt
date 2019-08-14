package com.tlz.tuiautomator

import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * xxx.
 * By tomlezen.
 * Date: 2019-08-13.
 * Time: 17:38.
 */
class ToastTest {

    private val automator = TUiautomator(TUiautomatorConfig(atxAgentIp = TestConfig.ATX_AGENT_SERVER))

    @Test
    fun testShow() {
        runBlocking {
            automator.toast.show("这是一个测试").handleTestResult()
        }
    }

    @Test
    fun testGetMessage() {
        runBlocking {
            automator.toast.getMessage(cacheTimeout = 600000).handleTestResult2()
        }
    }

}