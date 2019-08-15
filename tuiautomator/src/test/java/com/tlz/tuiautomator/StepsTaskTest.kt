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
            TestConfig.automator.application.stop("com.github.uiautomator").getOrThrow()
            TestConfig.automator.application.startWithMonkey("com.github.uiautomator").getOrThrow()
            1
        })
        task.addStep(1, TestConfig.automator.createStep {
            assert(TestConfig.automator.application.waitActivity("com.github.uiautomator.MainActivity").getOrThrow())
            delay(5_000)
            assert(TestConfig.automator.selector { text = "开发者选项" }.click().getOrThrow())
            delay(5_000)
            0
        })
    }

}