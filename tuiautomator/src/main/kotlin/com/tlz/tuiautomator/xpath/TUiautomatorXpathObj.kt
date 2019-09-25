package com.tlz.tuiautomator.xpath

import com.tlz.tuiautomator.TUiautomator
import com.tlz.tuiautomator.TUiautomatorResult
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.utils.TRect
import org.dom4j.Element
import org.dom4j.io.SAXReader
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.ByteArrayInputStream
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import kotlin.math.roundToInt

/**
 * Created by Tomlezen.
 * Date: 2019-08-16.
 * Time: 22:32.
 */
class TUiautomatorXpathObj(private val service: TUiautomatorService, private val xpath: String) : TUiautomatorXpath {

    override suspend fun all(): TUiautomatorResult<List<TUiautomatorXmlElement>> =
        runTCatching {
            val xml = service.device.dumpHierarchy().getOrThrow()
            // 先解析修改node
            val reader = SAXReader()
            val document = reader.read(ByteArrayInputStream(xml.toByteArray()))
            val rootEle = document.rootElement
            if (rootEle.name == "hierarchy") {
                val it = rootEle.elementIterator()
                while (it.hasNext()) {
                    parseNode(it.next())
                }
            }
            val newXml = document.asXML().replace("\n", "")
            val factory = XPathFactory.newInstance()
            val xpathObj = factory.newXPath()
            val nodeList = xpathObj.evaluate(
                xpath,
                InputSource(ByteArrayInputStream(newXml.toByteArray())),
                XPathConstants.NODESET
            ) as NodeList
            (0 until nodeList.length).map { TUiautomatorXmlElementObj(service, nodeList.item(it), xpath) }
        }

    override suspend fun click(): TUiautomatorResult<Boolean> =
        runTCatching {
            // 选取第一个匹配
            all().getOrThrow().getOrNull(0)?.click()?.getOrThrow() ?: false
        }

    override suspend fun text(): TUiautomatorResult<String?> =
        runTCatching {
            all().getOrThrow().getOrNull(0)?.text
        }

    override suspend fun bounds(): TUiautomatorResult<TRect> =
        runTCatching {
            val result = all().getOrThrow().getOrNull(0)?.attribute("bounds")
            // 匹配所有数字
            val values = TUiautomator.NUMBER_REGEX.findAll(result ?: "").map { it.value.toInt() }.toList()
            TRect(values[0], values[1], values[2], values[3])
        }

    override suspend fun center(xOffset: Float, yOffset: Float): TUiautomatorResult<Pair<Int, Int>> =
        runTCatching {
            val bounds = bounds().getOrThrow()
            (bounds.left + (bounds.width * xOffset).roundToInt()) to (bounds.top + (bounds.height * yOffset).roundToInt())
        }

    /**
     * 解析节点.
     * @param ele Element
     */
    private fun parseNode(ele: Element) {
        ele.name = ele.attributeValue("class").toString()
        val it = ele.elementIterator()
        while (it.hasNext()) {
            parseNode(it.next())
        }
    }
}