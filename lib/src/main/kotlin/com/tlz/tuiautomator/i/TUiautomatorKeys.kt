package com.tlz.tuiautomator.i

import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.annotations.TUiautomatorKeyName
import com.tlz.tuiautomator.i.handlers.TUiautomatorKeysHandler
import com.tlz.tuiautomator.utils.newTProxy

/**
 * key相关接口.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 16:50.
 */
interface TUiautomatorKeys {

    /**
     * 返回.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun back(): TUiautomatorResult<Boolean>

    /**
     * 回到主界面.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun home(): TUiautomatorResult<Boolean>

    /**
     * 菜单按钮.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun menu(): TUiautomatorResult<Boolean>

    /**
     * 左键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun left(): TUiautomatorResult<Boolean>

    /**
     * 右键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun right(): TUiautomatorResult<Boolean>

    /**
     * 上键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun up(): TUiautomatorResult<Boolean>

    /**
     * 下键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun down(): TUiautomatorResult<Boolean>

    /**
     * 中心键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun center(): TUiautomatorResult<Boolean>

    /**
     * 搜索键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun search(): TUiautomatorResult<Boolean>

    /**
     * 确认键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun enter(): TUiautomatorResult<Boolean>

    /**
     * 删除键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun delete(): TUiautomatorResult<Boolean>

    /**
     * 最近应用键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun recent(): TUiautomatorResult<Boolean>

    /**
     * 音量上键.
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorKeyName("volume_up")
    suspend fun volumeUp(): TUiautomatorResult<Boolean>

    /**
     * 音量下键。
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorKeyName("volume_down")
    suspend fun volumeDown(): TUiautomatorResult<Boolean>

    /**
     * 静音键.
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorKeyName("volume_mute")
    suspend fun volumeMute(): TUiautomatorResult<Boolean>

    /**
     * 相机键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun camera(): TUiautomatorResult<Boolean>

    /**
     * 电源键.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun power(): TUiautomatorResult<Boolean>

    companion object {
        operator fun invoke(service: TUiautomatorService): TUiautomatorKeys =
            newTProxy(TUiautomatorKeysHandler(service))
    }
}