package com.tlz.tuiautomator.step

import java.lang.Exception

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
     * 运行状态回调.
     * (注意：这里回调实在异步线程)
     * @param block Function1<[@kotlin.ParameterName] Boolean, Unit>
     * @param isAdd Boolean
     */
    fun runningStateCallback(block: (running: Boolean) -> Unit, isAdd: Boolean)

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

    fun registerRunningListener(listener: OnTaskRunningListener, resgieter: Boolean)

    /**
     * 任务运行监听.
     */
    interface OnTaskRunningListener {

        /**
         * 当前准备执行的step.
         * @param parentStepId Int?
         * @param stepId Int
         */
        fun onRunStep(parentStepId: Int?, stepId: Int)

        /**
         * step运行异常.
         * @param stepId Int
         * @param exception Exception
         */
        fun onStepRunException(stepId: Int, exception: Exception)

        /**
         * step重试.
         * @param stepId Int
         * @param retryCount Int 当前重试次数
         * @param maxRetryCount Int 最大从事次数
         */
        fun onStepRetry(stepId: Int, retryCount: Int, maxRetryCount: Int)

        /**
         * 任务重新开始.
         * @param startStepId Int?
         */
        fun onTaskRestart(startStepId: Int?)
    }
}