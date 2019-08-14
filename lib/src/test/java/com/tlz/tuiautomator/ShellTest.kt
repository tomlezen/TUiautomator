package com.tlz.tuiautomator

import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * shell命令测试.
 * By tomlezen.
 * Date: 2019-08-14.
 * Time: 14:28.
 */
class ShellTest {

    @Test
    fun testShell() {
        runBlocking {
            TestConfig.automator.shell.execute("am force-stop com.lxt.app.dev").handleTestResult2()
        }
    }

}