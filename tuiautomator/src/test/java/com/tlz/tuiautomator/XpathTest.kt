package com.tlz.tuiautomator

import com.tlz.tuiautomator.utils.assert
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Created by Tomlezen.
 * Date: 2019-08-16.
 * Time: 22:40.
 */
class XpathTest {

    @Test
    fun testAll() {
        runBlocking {
//            TestConfig.automator.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]")
//            TestConfig.automator.xpath("//node")
//            TestConfig.automator.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]")
//                .all()
//                .handleTestResult2()
            (TestConfig.automator.xpath("//*[@resource-id=\"com.tencent.qgame:id/ivTitleName\"]").text().getOrThrow() === "抽奖").assert()
        }
    }

}