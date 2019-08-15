package com.tlz.tuiautomator

import com.tlz.tuiautomator.step.TUiautomatorStepsTask
import kotlinx.coroutines.delay
import org.junit.Test

/**
 * 步骤任务测试.
 * By tomlezen.
 * Date: 2019-08-15.
 * Time: 17:07.
 */
class StepsTaskTest {

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
            assert(TestConfig.automator.application.waitActivity("com.tencent.qgame.presentation.activity.MainActivity").getOrThrow())
            delay(5_000)
            // 判断下是否有弹窗出现 有弹窗点击取消
            TestConfig.automator.selector { text = "取消" }.click()
            delay(3_000)
            TestConfig.automator.selector { text = "取消" }.click()
            delay(3_000)
            assert(TestConfig.automator.selector { text = "关注" }.click().getOrThrow())
            delay(3_000)
            // 下拉刷新一哈
            assert(TestConfig.automator.gestures.swipe().getOrThrow())
//            assert(TestConfig.automator.keys.back().getOrThrow())
//            delay(5_000)
            0
        })
    }

}