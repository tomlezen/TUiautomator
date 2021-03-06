package com.tlz.tuiautomator

import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Created by Tomlezen.
 * Date: 2019-08-02.
 * Time: 20:32.
 */
class DeviceTest {

    private val automator = TUiautomator(TUiautomatorConfig(atxAgentIp = TestConfig.ATX_AGENT_SERVER))

    @Test
    fun testInfo() {
        runBlocking {
            automator.device.info().handleTestResult2()
        }
    }

    @Test
    fun testDumpHierarchy() {
        runBlocking {
            automator.device.dumpHierarchy().handleTestResult2()
        }
    }

}