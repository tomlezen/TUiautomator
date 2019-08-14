package com.tlz.tuiautomator

import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * 按键相关api测试.
 * By tomlezen.
 * Date: 2019-07-26.
 * Time: 16:31.
 */
class KeysTest {

    private val automator = TUiautomator(TUiautomatorConfig(atxAgentIp = TestConfig.ATX_AGENT_SERVER))

    @Test
    fun back() {
        runBlocking {
            automator.keys.back().handleTestResult()
        }
    }

    @Test
    fun home() {
        runBlocking {
            automator.keys.home().handleTestResult()
        }
    }

    @Test
    fun menu() {
        runBlocking {
            automator.keys.menu().handleTestResult()
        }
    }

    @Test
    fun left() {
        runBlocking {
            automator.keys.left().handleTestResult()
        }
    }

    @Test
    fun right() {
        runBlocking {
            automator.keys.right().handleTestResult()
        }
    }

    @Test
    fun up() {
        runBlocking {
            automator.keys.up().handleTestResult()
        }
    }

    @Test
    fun down() {
        runBlocking {
            automator.keys.down().handleTestResult()
        }
    }

    @Test
    fun center() {
        runBlocking {
            automator.keys.center().handleTestResult()
        }
    }

    @Test
    fun search() {
        runBlocking {
            automator.keys.search().handleTestResult()
        }
    }


    @Test
    fun enter() {
        runBlocking {
            automator.keys.enter().handleTestResult()
        }
    }

    @Test
    fun delete() {
        runBlocking {
            automator.keys.delete().handleTestResult()
        }
    }

    @Test
    fun recent() {
        runBlocking {
            automator.keys.recent().handleTestResult()
        }
    }

    @Test
    fun volumeUp() {
        runBlocking {
            automator.keys.volumeUp().handleTestResult()
        }
    }

    @Test
    fun volumeDown() {
        runBlocking {
            automator.keys.volumeDown().handleTestResult()
        }
    }

    @Test
    fun volumeMute() {
        runBlocking {
            automator.keys.volumeMute().handleTestResult()
        }
    }

    @Test
    fun camera() {
        runBlocking {
            automator.keys.camera().handleTestResult()
        }
    }

    @Test
    fun power() {
        runBlocking {
            automator.keys.power().handleTestResult()
        }
    }
}