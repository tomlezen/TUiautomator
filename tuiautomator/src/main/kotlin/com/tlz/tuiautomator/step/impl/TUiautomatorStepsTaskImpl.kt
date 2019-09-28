package com.tlz.tuiautomator.step.impl

import com.tlz.tuiautomator.step.TUiautomatorStep
import com.tlz.tuiautomator.step.TUiautomatorStepsTask
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import java.util.concurrent.ConcurrentHashMap

/**
 * By tomlezen.
 * Date: 2019-08-15.
 * Time: 15:46.
 */
class TUiautomatorStepsTaskImpl : TUiautomatorStepsTask {

    private var rootJob: Job? = null

    /** 用于对id去重复. */
    private val stepIdSet = mutableSetOf<Int>()

    /** 所有步骤. */
    private val steps = ConcurrentHashMap<Int, TUiautomatorStep>()

    /** 开始步骤id. */
    private var _startStepId: Int? = null

    private val taskRunningListenerSet = mutableSetOf<TUiautomatorStepsTask.OnTaskRunningListener>()

    /** 运行状态回调. */
    private val runningStateCallbacks = mutableSetOf<(Boolean) -> Unit>()

    private var _isRunning = false
        set(value) {
            field = value
            runningStateCallbacks.forEach { it.invoke(value) }
        }

    override val isRunning: Boolean
        get() = _isRunning

    override fun runningStateCallback(block: (running: Boolean) -> Unit, isAdd: Boolean) {
        if (isAdd) {
            runningStateCallbacks += block
        } else {
            runningStateCallbacks -= block
        }
    }

    @Synchronized
    override fun addStep(id: Int, step: TUiautomatorStep) {
        stepIdSet += id
        steps[id] = step
    }

    override fun getStepById(id: Int): TUiautomatorStep? = steps[id]

    @Synchronized
    override fun start(startStepId: Int) {
        require(stepIdSet.contains(startStepId)) { "not found stepId: $startStepId" }
        require(!steps.isEmpty()) { "step is empty" }
        if (isRunning) return

        _startStepId = startStepId
        rootJob = GlobalScope.async {
            runStep(null, startStepId)
        }
        _isRunning = true
    }

    override fun restart(startStepId: Int?) {
        stop()
        start(startStepId ?: _startStepId ?: return)
        taskRunningListenerSet.forEach { it.onTaskRestart(startStepId) }
    }

    override fun stop() {
        rootJob?.cancel()
        _isRunning = false
    }

    override fun destroy() {
        stop()
        stepIdSet.clear()
        steps.clear()
        runningStateCallbacks.clear()
    }

    override fun registerRunningListener(
        listener: TUiautomatorStepsTask.OnTaskRunningListener,
        resgieter: Boolean
    ) {
        if (resgieter) {
            taskRunningListenerSet += listener
        } else {
            taskRunningListenerSet -= listener
        }
    }

    /**
     * 执行步骤.
     * @param parentStepId Int?
     * @param stepId Int
     */
    private suspend fun runStep(parentStepId: Int?, stepId: Int) {
        if (!isRunning) return
        taskRunningListenerSet.forEach { it.onRunStep(parentStepId, stepId) }
        val step = steps[stepId]
        if (step != null) {
            step.parentStepId = parentStepId
            var retryCount = 0
            var nextStepId: Int? = null
            while (retryCount <= step.retryCount && isRunning) {
                try {
                    nextStepId = step.run(this)
                    // 重置重试次数
                    retryCount = 0
                    break
                } catch (e: Exception) {
                    retryCount++
                    taskRunningListenerSet.forEach { it.onStepRunException(stepId, e) }
                }
            }
            if (retryCount > step.retryCount) {
                // 如果没有处理则直接重新开始.
                if (!step.onFailureStrategy(this) && isRunning) {
                    restart()
                }
            } else if (nextStepId != null) {
                runStep(stepId, nextStepId)
            } else {
                // 停止任务
                stop()
            }
        } else {
            throw NullPointerException("not found step by id = $stepId")
        }
    }
}