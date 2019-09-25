package com.tlz.tuiautomator.xpath

import com.sun.org.apache.xerces.internal.impl.xs.opti.AttrImpl
import com.tlz.tuiautomator.TUiautomator
import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.i.handlers.TUiautomatorDeviceHandler
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.utils.TRect
import org.w3c.dom.Node

/**
 * Created by Tomlezen.
 * Date: 2019-08-17.
 * Time: 13:02.
 */
class TUiautomatorXmlElementObj(
    private val automator: TUiautomator,
    private val node: Node,
    private val parentXpath: String
) : TUiautomatorXmlElement {

    /** 范围. */
    override val rect: TRect
        get() = TRect().apply {
            TUiautomatorDeviceHandler.BOUND_REGEX.findAll(attribute("bounds"))
                .forEachIndexed { index, matchResult ->
                    if (index == 0) {
                        left = matchResult.groupValues[1].toInt()
                        top = matchResult.groupValues[2].toInt()
                    } else if (index == 1) {
                        right = matchResult.groupValues[1].toInt()
                        bottom = matchResult.groupValues[2].toInt()
                    }
                }
        }

    /**
     * 偏移.
     * @param px Float
     * @param py Float
     * @return Pair<Int, Int>
     */
    override fun offset(px: Float, py: Float): Pair<Int, Int> =
        rect.let {
            it.left + (it.width * px).toInt() to it.top + (it.height * py).toInt()
        }

    /** 中心点. */
    override val center: Pair<Int, Int>
        get() = offset(.5f, .5f)

    override val text: String
        get() = attribute("text")

    /**
     * 获取属性.
     * @param name String
     * @return String
     */
    override fun attribute(name: String): String =
        node.attributes.let {
            (0 until it.length).map { index -> it.item(index)}
                .find { n -> n.localName == name }?.textContent.toString()
        }

    override val children: List<TUiautomatorXmlElement> by lazy {
        (0 until node.childNodes.length).asSequence()
            .map {
                node.childNodes.item(it)
            }.filter {
                it.nodeName != "#text"
            }.map {
                TUiautomatorXmlElementObj(automator, it, parentXpath + "/" + node.nodeName)
            }.toList()
    }

    override suspend fun click(): TUiautomatorResult<Boolean> =
        runTCatching {
            val (x, y) = center
            automator.gestures.click(x, y).getOrThrow()
        }

    override fun toString(): String = "xpath = $parentXpath; nodeName = ${node.nodeName}"
}