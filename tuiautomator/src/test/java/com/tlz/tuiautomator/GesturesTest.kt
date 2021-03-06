package com.tlz.tuiautomator

import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * 手势相关api测试.
 * By tomlezen.
 * Date: 2019-07-26.
 * Time: 16:31.
 */
class GesturesTest {

    private val automator = TUiautomator(TUiautomatorConfig(atxAgentIp = TestConfig.ATX_AGENT_SERVER))

    @Test
    fun click() {
        runBlocking {
            automator.gestures.click(100, 100).handleTestResult()
        }
    }

    @Test
    fun longClick() {
        runBlocking {
            automator.gestures.longClick(100, 100).handleTestResult()
        }
    }

    @Test
    fun doubleClick() {
        runBlocking {
            automator.gestures.doubleClick(100, 100).handleTestResult()
        }
    }

    @Test
    fun swipe() {
        runBlocking {
            automator.gestures.swipe(300, 600, 300, 300, 20).handleTestResult()
        }
    }

    @Test
    fun swipeByPercent() {
        runBlocking {
            automator.gestures.swipeByPercent(0.6f, .9f, 0.6f, 0.2f, 20).handleTestResult()
        }
    }

    @Test
    fun swipe2() {
        runBlocking {
            automator.gestures.swipe(100 to 100, 400 to 100).handleTestResult()
        }
    }

    @Test
    fun drag() {
        runBlocking {
            automator.gestures.drag(300, 300, 300, 1000).handleTestResult()
        }
    }

}