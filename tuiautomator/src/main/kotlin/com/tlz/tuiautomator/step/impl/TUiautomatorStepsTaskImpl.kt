package com.tlz.tuiautomator.step.impl

import com.tlz.tuiautomator.onFailure
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.step.TUiautomatorStep
import com.tlz.tuiautomator.step.TUiautomatorStepsTask
import kotlinx.coroutines.*
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap

/**
 * By tomlezen.
 * Date: 2019-08-15.
 * Time: 15:46.
 */
class TUiautomatorStepsTaskImpl(private val isTestMode: Boolean) : TUiautomatorStepsTask {

    private var rootJob: Job? = null

    /** 用于对id去重复. */
    private val steIdSet = mutableSetOf<Int>()

    /** 所有步骤. */
    private val steps = ConcurrentHashMap<Int, TUiautomatorStep>()

    /** 开始不走id. */
    private var _startStepId: Int? = null

    override val isRunning: Boolean
        get() = rootJob?.isActive == true

    @Synchronized
    override fun addStep(id: Int, step: TUiautomatorStep) {
        steIdSet += id
        steps[id] = step
    }

    override fun getStepById(id: Int): TUiautomatorStep? = steps[id]

    @Synchronized
    override fun start(startStepId: Int) {
        if (isRunning || steps.isEmpty() || !steIdSet.contains(startStepId)) return

        _startStepId = startStepId
//        rootJob = if (isTestMode) {
//
//
//        } else {
//            GlobalScope.async {
//
//            }
//        }
        runBlocking {
            runStep(null, startStepId)
        }
    }

    override fun restart(startStepId: Int?) {
        stop()
        start(startStepId ?: _startStepId ?: return)
    }

    override fun stop() {
        rootJob?.cancel()
    }

    override fun destroy() {
        stop()
        steIdSet.clear()
        steps.clear()
    }

    /**
     * 执行步骤.
     * @param parentStepId Int?
     * @param stepId Int
     */
    private suspend fun runStep(parentStepId: Int?, stepId: Int) {
        val step = steps[stepId]
        if (step != null) {
            step.parentStepId = parentStepId
            var retryCount = 0
            var nextStepId: Int? = null
            while (retryCount <= step.retryCount) {
                try {
                    nextStepId = step.run(this)
                    break
                } catch (e: Exception) {
                    retryCount++
                }
            }
            if (retryCount > step.retryCount) {
                // 如果没有处理则直接重新开始.
                if (!step.onFailureStrategy(this)) {
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