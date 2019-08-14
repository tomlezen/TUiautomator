package com.tlz.tuiautomator.i

import com.tlz.tuiautomator.TUiautomatorMethods
import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.annotations.TUiautomatorKeyName
import com.tlz.tuiautomator.i.handlers.TUiautomatorToastHandler
import com.tlz.tuiautomator.utils.newTProxy

/**
 * Toast相关api.
 * By tomlezen.
 * Date: 2019-08-13.
 * Time: 17:05.
 */
interface TUiautomatorToast {

    /**
     * 显示Toast。
     * @param message String
     * @param duration Long
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorKeyName(TUiautomatorMethods.Toast.SHOW)
    fun show(message: String, duration: Long = 2000): TUiautomatorResult<Boolean>

    /**
     * 获取显示的Toast信息.
     * 这个api好像有问题.
     * @param waitTimeout Long 等待超时时间
     * @param cacheTimeout Long 获取最新的toast文字时间范围
     * @param default String?
     * @return TUiautomatorResult<String?>
     */
    @TUiautomatorKeyName(TUiautomatorMethods.Toast.GET_MESSAGE)
    fun getMessage(
        waitTimeout: Long = 10_000,
        cacheTimeout: Long = 10_000,
        default: String? = null
    ): TUiautomatorResult<String?>

    /***
     * 清空Toast信息缓存.
     * @return TUiautomatorResult<Boolean>
     */
    @TUiautomatorKeyName(TUiautomatorMethods.Toast.RESET)
    fun reset(): TUiautomatorResult<Boolean>

    companion object {
        operator fun invoke(service: TUiautomatorService): TUiautomatorToast =
            newTProxy(TUiautomatorToastHandler(service))
    }
}