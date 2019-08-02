package com.tlz.tuiautomator.i

import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.net.model.TDeviceInfo

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
    suspend fun info(): TUiautomatorResult<TDeviceInfo>

    /**
     * 获取窗口大小.
     * @return TUiautomatorResult<Pair<Int, Int>>
     */
    suspend fun windowSize(): TUiautomatorResult<Pair<Int, Int>>

}