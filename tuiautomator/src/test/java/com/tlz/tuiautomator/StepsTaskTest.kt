package com.tlz.tuiautomator

import com.tlz.tuiautomator.selector.TUiautomatorSwipeDirection
import com.tlz.tuiautomator.step.TUiautomatorStepsTask
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * 步骤任务测试.
 * By tomlezen.
 * Date: 2019-08-15.
 * Time: 17:07.
 */
class StepsTaskTest {

    private val names = mutableListOf<String>()
    private val findNames = mutableListOf<String>()

    @Test
    fun test1() {
        runBlocking {
            val eleList =
                TestConfig.automator.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.TextView")
                    .all()
                    .getOrThrow()
            println(eleList.joinToString { it.text })
        }
    }

    @Test
    fun testStepTask() {
        val task = TestConfig.automator.createTask()
        initSteps(task)
        task.start(0)
    }

    private fun initSteps(task: TUiautomatorStepsTask) {
        task.addStep(0, TestConfig.automator.createStep {
            TestConfig.automator.application.stop("com.tencent.qgame").getOrThrow()
            delay(1_000)
            TestConfig.automator.application.startWithMonkey("com.tencent.qgame").getOrThrow()
            1
        })
        task.addStep(1, TestConfig.automator.createStep {
            assert(
                TestConfig.automator.application.waitActivity(
                    "com.tencent.qgame.presentation.activity.MainActivity",
                    20000
                ).getOrThrow()
            )
            delay(3_000)
            // 判断下是否有弹窗出现 有弹窗点击取消
            TestConfig.automator.selector { text = "取消" }.click()
            delay(2_000)
            TestConfig.automator.selector { text = "取消" }.click()
            delay(2_000)
            assert(TestConfig.automator.selector { text = "关注" }.click().getOrThrow())
            2
        })
        // 获取关注列表
        task.addStep(2, TestConfig.automator.createStep {
            delay(5_000)
            // 下拉刷新一哈
            assert(TestConfig.automator.selector {
                scrollable = true
            }.swipe(TUiautomatorSwipeDirection.DOWN).getOrThrow())
            // 等一会准备读取
            delay(3_000)
            val eleList =
                TestConfig.automator.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.FrameLayout[2]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout/android.view.View")
                    .all()
                    .getOrThrow()
            // 名字要移除 ... 符号
            eleList.mapTo(names) { it.attribute("content-desc").replace("...", "") }

            3
        })
        // 进入抽奖页面
        task.addStep(3, TestConfig.automator.createStep {
            delay(2_000)
            // 回到首页
            assert(TestConfig.automator.selector { text = "首页" }.click().getOrThrow())
            // 等一会准备读取
            delay(3_000)
            // 点击互动专区
            assert(TestConfig.automator.selector { text = "互动专区" }.click().getOrThrow())
            delay(3_000)
            // 点击抽奖
            assert(TestConfig.automator.selector { text = "抽奖" }.click().getOrThrow())
            4
        })
        // 找到关注抽奖主播
        task.addStep(4, TestConfig.automator.createStep {
            delay(2_000)
            // 先刷新哈
            assert(TestConfig.automator.selector {
                scrollable = true
            }.swipe(TUiautomatorSwipeDirection.DOWN).getOrThrow())
            findNames()

            null
        })
    }

    private suspend fun findNames() {
        // 获取已经显示的列表
        val eleList =
            TestConfig.automator.xpath("//androidx.recyclerview.widget.RecyclerView/android.view.ViewGroup/android.widget.RelativeLayout/android.widget.RelativeLayout/android.widget.TextView")
                .all()
                .getOrThrow()
        eleList.filter {
            names.find { n -> it.text.startsWith(n) && !findNames.contains(n) }?.let { n ->
                findNames.add(n)
            } != null
        }.forEach {
            it.click()
        }
        // 判断是否到底了
        if (TestConfig.automator.selector { text = "没有更多数据了" }.exists().getOrThrow()) {

        } else {
            // 滑动一屏幕
//            val scrollDis = TestConfig.automator.xpath("//androidx.recyclerview.widget.RecyclerView")
//                .all()
//                .getOrThrow()
//                .first()
//                .rect
//                .height

            TestConfig.automator.selector { scrollable = true }.scroll.scrollTo {
                text = eleList.last().text
            }

            delay(5000)
            findNames()
        }
    }

}