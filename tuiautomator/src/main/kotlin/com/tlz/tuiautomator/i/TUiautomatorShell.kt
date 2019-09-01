package com.tlz.tuiautomator.i

import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.i.handlers.TUiautomatorShellHandler
import com.tlz.tuiautomator.utils.newTProxy

/**
 * Shell命令操作.
 * By tomlezen.
 * Date: 2019-08-13.
 * Time: 17:53.
 */
interface TUiautomatorShell {

    /**
     * 执行cmd命令.
     * @param cmd String
     * @param timeout Long
     * @return TUiautomatorResult<String>
     */
    suspend fun execute(cmd: String, timeout: Long = 60_000): TUiautomatorResult<String>

    companion object {
        operator fun invoke(service: TUiautomatorService): TUiautomatorShell =
            newTProxy(TUiautomatorShellHandler(service))
    }
}

