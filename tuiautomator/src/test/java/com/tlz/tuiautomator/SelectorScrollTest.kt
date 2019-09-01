package com.tlz.tuiautomator

import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Created by Tomlezen.
 * Date: 2019-07-27.
 * Time: 10:18.
 */
class SelectorScrollTest {

    private val automator = TUiautomator(TUiautomatorConfig(atxAgentIp = TestConfig.ATX_AGENT_SERVER))

    @Test
    fun testScrollToEnd() {
        runBlocking {
            automator.selector {
                scrollable = true
            }.scroll.scrollToEnd().handleTestResult()
        }
    }

    @Test
    fun testScrollToBeginning() {
        runBlocking {
            automator.selector {
                scrollable = true
            }.scroll.scrollToBeginning().handleTestResult()
        }
    }

    @Test
    fun testScrollTo() {
        runBlocking {
            automator.selector {
                scrollable = true
            }.scroll.scrollTo {
                text = "个性主题"
            }.handleTestResult()
        }
    }

    @Test
    fun testScrollForward() {
        runBlocking {
            automator.selector {
                scrollable = true
            }.scroll.scrollForward().handleTestResult()
        }
    }

    @Test
    fun testScrollBackward() {
        runBlocking {
            automator.selector {
                scrollable = true
            }.scroll.scrollBackward().handleTestResult()
        }
    }

}