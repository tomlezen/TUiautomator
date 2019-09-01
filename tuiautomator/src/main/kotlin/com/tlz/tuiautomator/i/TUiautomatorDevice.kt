package com.tlz.tuiautomator.i

import com.tlz.tuiautomator.TUiautomatorMethods
import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.annotations.TUiautomatorMethodName
import com.tlz.tuiautomator.i.handlers.TUiautomatorDeviceHandler
import com.tlz.tuiautomator.net.model.TDeviceInfo
import com.tlz.tuiautomator.net.model.TUiautomatorHierarchy
import com.tlz.tuiautomator.utils.newTProxy

/**
 * 设备信息相关接口.
 * By tomlezen.
 * Date: 2019-08-02.
 * Time: 16:02.
 */
interface TUiautomatorDevice {

    /**
     * 获取设备信息.
     * @return TUiautomatorResult<TDeviceInfo>
     */
    @TUiautomatorMethodName(TUiautomatorMethods.Device.INFO)
    suspend fun info(): TUiautomatorResult<TDeviceInfo>

    /**
     * 获取窗口大小.
     * @return TUiautomatorResult<Pair<Int, Int>>
     */
    suspend fun windowSize(): TUiautomatorResult<Pair<Int, Int>>

    /**
     * 提取整个ui结构.
     * @return TUiautomatorResult<String>
     */
    suspend fun dumpHierarchy(): TUiautomatorResult<String>

    companion object {
        operator fun invoke(service: TUiautomatorService): TUiautomatorDevice =
            newTProxy(TUiautomatorDeviceHandler(service))
    }
}