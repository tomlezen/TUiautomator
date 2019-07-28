package com.tlz.tuiautomator

import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Created by Tomlezen.
 * Date: 2019-07-27.
 * Time: 13:22.
 */
class SelectorFlingTest{

    private val automator = TUiautomator(TUiautomatorConfig(atxAgentIp = TestConfig.ATX_AGENT_SERVER))

    @Test
    fun textFlingToEnd(){
        runBlocking {
            automator.selector {
                scrollable = true
            }.fling.flingToEnd().handleTestResult()
        }
    }

}