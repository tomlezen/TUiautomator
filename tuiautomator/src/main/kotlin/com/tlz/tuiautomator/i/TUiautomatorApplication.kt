package com.tlz.tuiautomator.i

import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.annotations.TUiautomatorFormatParam
import com.tlz.tuiautomator.annotations.TUiautomatorShellCmd
import com.tlz.tuiautomator.i.handlers.TUiautomatorApplicationHandler
import com.tlz.tuiautomator.net.model.AppInfoResult
import com.tlz.tuiautomator.net.model.SessionResult
import com.tlz.tuiautomator.utils.newTProxy

/**
 * 应用相关api.
 * By tomlezen.
 * Date: 2019-08-14.
 * Time: 15:00.
 */
interface TUiautomatorApplication {

    /**
     * 当前app.
     * @return TUiautomatorResult<Pair<String, String>> 包名、activity名.
     */
    fun currentApp(): TUiautomatorResult<Pair<String, String>>

    /**
     * 等待Activity.
     * @param activity String
     * @param timeout Long
     * @return TUiautomatorResult<Boolean>
     */
    fun waitActivity(activity: String, timeout: Long = 10_000): TUiautomatorResult<Boolean>

    /**
     * 启动应用.
     * @param pkgName String
     * @return TUiautomatorResult<SessionResult>
     */
    fun start(pkgName: String): TUiautomatorResult<SessionResult>

    /**
     * 使用monkey命令启动app.
     * @param pkgName String
     * @return TUiautomatorResult<String?>
     */
    @TUiautomatorShellCmd("monkey -p %s -c android.intent.category.LAUNCHER 1")
    fun startWithMonkey(@TUiautomatorFormatParam pkgName: String): TUiautomatorResult<String?>

    /**
     * 打开Activity.
     * @param pkgName String
     * @param activity String
     * @return TUiautomatorResult<String?>
     */
    @TUiautomatorShellCmd("am start -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -W -n %s%2F%s")
    fun startActivity(@TUiautomatorFormatParam pkgName: String, @TUiautomatorFormatParam activity: String): TUiautomatorResult<String?>

    /**
     * 关闭app.
     * @param pkgName String
     * @return TUiautomatorResult<String?>
     */
    @TUiautomatorShellCmd("am force-stop %s")
    fun stop(@TUiautomatorFormatParam pkgName: String): TUiautomatorResult<String?>

    /**
     * 关闭所有app.
     * @param excludes Array<out String> 不关闭的app.
     * @return TUiautomatorResult<Int> 关闭的app个数
     */
    fun stopAll(vararg excludes: String): TUiautomatorResult<Int>

    /**
     * 停止并清空app数据.
     * @param pkgName String
     * @return TUiautomatorResult<String?>
     */
    @TUiautomatorShellCmd("pm clear %s")
    fun clear(@TUiautomatorFormatParam pkgName: String): TUiautomatorResult<String?>

    /**
     * 卸载app.
     * @param pkgName String
     * @return TUiautomatorResult<String?>
     */
    @TUiautomatorShellCmd("pm uninstall %s")
    fun uninstall(@TUiautomatorFormatParam pkgName: String): TUiautomatorResult<String?>

    /**
     * 获取应用信息.
     * @param pkgName String
     * @return TUiautomatorResult<AppInfoResult>
     */
    fun info(pkgName: String): TUiautomatorResult<AppInfoResult>

    companion object {
        operator fun invoke(service: TUiautomatorService): TUiautomatorApplication =
            newTProxy(TUiautomatorApplicationHandler(service))
    }
}