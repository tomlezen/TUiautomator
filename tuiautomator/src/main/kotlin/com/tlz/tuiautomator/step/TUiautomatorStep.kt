package com.tlz.tuiautomator.step

import com.tlz.tuiautomator.TUiautomator
import com.tlz.tuiautomator.exceptions.TUiautomatorStepRunException

/**
 * 步骤.
 * By tomlezen.
 * Date: 2019-08-15.
 * Time: 15:28.
 * @property retryCount Int 重试次数.
 */
abstract class TUiautomatorStep(protected val automator: TUiautomator, val retryCount: Int = 2) {

    /** 父任务id. */
    var parentStepId: Int? = null

    /**
     * 运行步骤.
     * @param task TUiautomatorStepsTask
     * @return Int?
     * @throws TUiautomatorStepRunException
     */
    @Throws(TUiautomatorStepRunException::class)
    abstract suspend fun run(task: TUiautomatorStepsTask): Int?

    /**
     * 步骤失败后策略.
     * 默认重新开始.
     * @param task TUiautomatorStepsTask
     */
    open fun onFailureStrategy(task: TUiautomatorStepsTask): Boolean = false
}