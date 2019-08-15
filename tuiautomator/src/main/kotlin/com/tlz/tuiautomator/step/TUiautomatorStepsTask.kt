package com.tlz.tuiautomator.step

import org.jetbrains.annotations.TestOnly

/**
 * 步骤容器，用于对步骤的管理.
 * By tomlezen.
 * Date: 2019-08-15.
 * Time: 15:32.
 */
interface TUiautomatorStepsTask {

    /** 当前任务是否运行. */
    val isRunning: Boolean

    /**
     * 添加步骤.
     * @param id Int 必须唯一.
     * @param step TUiautomatorStep
     */
    fun addStep(id: Int, step: TUiautomatorStep)

    /**
     * 根据id获取步骤.
     * @param id Int
     * @return TUiautomatorStep?
     */
    fun getStepById(id: Int): TUiautomatorStep?

    /**
     * 开始运行任务.
     * @param startStepId Int 起始步骤id.
     */
    fun start(startStepId: Int)

    /**
     * 重新开始.
     * @param startStepId Int? 从某个步骤开始.
     */
    fun restart(startStepId: Int? = null)

    /**
     * 停止执行.
     */
    fun stop()

    /**
     * 销毁任务.
     */
    fun destroy()
}