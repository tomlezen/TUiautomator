package com.tlz.tuiautomator.selector

import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.selector.fling.TUiFling
import com.tlz.tuiautomator.selector.model.TUiInfo
import com.tlz.tuiautomator.selector.scroll.TUiScroll

/**
 * ui相关接口.
 * By tomlezen.
 * Date: 2019-07-26.
 * Time: 17:31.
 */
interface TUiautomatorSelectors {

    val scroll: TUiScroll

    val fling: TUiFling

    /**
     * 是否存在.
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun exists(): TUiautomatorResult<Boolean>

    suspend fun info(): TUiautomatorResult<TUiInfo>
}