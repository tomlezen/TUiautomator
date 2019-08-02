package com.tlz.tuiautomator.selector

import android.support.annotation.IntRange
import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.selector.fling.TUiFling
import com.tlz.tuiautomator.selector.model.TUiInfo
import com.tlz.tuiautomator.selector.scroll.TUiScroll
import com.tlz.tuiautomator.utils.TRect

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

    /**
     * ui信息.
     * @return TUiautomatorResult<TUiInfo>
     */
    suspend fun info(): TUiautomatorResult<TUiInfo>

    /**
     * 获取ui边界.
     * @return TUiautomatorResult<TRect>
     */
    suspend fun bounds(): TUiautomatorResult<TRect>

    /**
     * ui中心点.
     * @param xOffset Float x轴偏移百分比
     * @param yOffset Float y轴偏移百分比
     * @return TUiautomatorResult<Pair<Int, Int>>
     */
    suspend fun center(xOffset: Float = .5f, yOffset: Float = .5f): TUiautomatorResult<Pair<Int, Int>>

    /**
     * 长按.
     * @param duration Long?
     * @param timeout Int?
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun longClick(duration: Long? = null, timeout: Int? = null): TUiautomatorResult<Boolean>

    /**
     * 拖拽（注意x,y为百分比）.
     * @param x Float
     * @param y Float
     * @return TUiautomatorResult<Boolean>
     */
    suspend fun dragTo(x: Float, y: Float): TUiautomatorResult<Boolean>
}