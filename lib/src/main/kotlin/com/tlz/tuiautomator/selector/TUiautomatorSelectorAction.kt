package com.tlz.tuiautomator.selector

/**
 * Created by Tomlezen.
 * Date: 2019-07-27.
 * Time: 12:32.
 */
interface TUiautomatorSelectorAction {
    /**
     * 提交操作.
     * @param methodName String
     * @param args Array<Any>?
     */
    suspend fun submit(methodName: String, args: Array<out Any>?): Any
}