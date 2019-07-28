package com.tlz.tuiautomator

import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Created by Tomlezen.
 * Date: 2019-07-28.
 * Time: 14:01.
 */
class SelectorTest {

    private val automator = TUiautomator(TUiautomatorConfig(atxAgentIp = TestConfig.ATX_AGENT_SERVER))

    @Test
    fun testExist(){
        runBlocking {
            automator.selector { text = "个性主题" }.exists().handleTestResult()
        }
    }

    @Test
    fun testInfo(){
        runBlocking {
            automator.selector { text = "个性主题" }.info().handleTestResult2()
        }
    }

}