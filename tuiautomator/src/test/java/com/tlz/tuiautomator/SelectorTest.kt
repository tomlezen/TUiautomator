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
    fun testExist() {
        runBlocking {
            automator.selector { text = "个性主题" }.exists().handleTestResult()
        }
    }

    @Test
    fun testInfo() {
        runBlocking {
            automator.selector { text = "个性主题" }.info().handleTestResult2()
        }
    }

    @Test
    fun testLongClick() {
        runBlocking {
            automator.selector { text = "开发者选项" }.longClick().handleTestResult2()
            automator.selector { text = "测试" }.longClick().handleTestResult2()
        }
    }

    @Test
    fun testDragTo() {
        runBlocking {
            automator.selector { text = "企鹅电竞" }.dragTo(.5f, .1f)
        }
    }

}