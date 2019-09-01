package com.tlz.tuiautomator.i.handlers

import com.google.gson.internal.LinkedTreeMap
import com.tlz.tuiautomator.TUiautomatorService
import com.tlz.tuiautomator.i.TUiautomatorDevice
import com.tlz.tuiautomator.net.model.TDeviceInfo
import com.tlz.tuiautomator.net.model.TUiautomatorHierarchy
import com.tlz.tuiautomator.net.request.jsonrpcRequest
import com.tlz.tuiautomator.runTCatching
import com.tlz.tuiautomator.utils.*
import kotlinx.coroutines.runBlocking
import org.dom4j.Element
import org.dom4j.io.SAXReader
import java.io.ByteArrayInputStream
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import javax.xml.transform.sax.SAXResult

/**
 * Created by Tomlezen.
 * Date: 2019-08-02.
 * Time: 20:17.
 */
class TUiautomatorDeviceHandler(private val service: TUiautomatorService) : InvocationHandler {

    @Suppress("IMPLICIT_CAST_TO_ANY")
    override fun invoke(proxy: Any?, method: Method, params: Array<out Any?>?): Any? =
        runBlocking {
            val methodName = method.tMethodName
            runTCatching {
                when (method.name) {
                    TUiautomatorDevice::info.name -> {
                        ((service rq jsonrpcRequest(method = methodName)) as LinkedTreeMap<*, *>).toJson()
                            .toObj<TDeviceInfo>()
                    }
                    TUiautomatorDevice::windowSize.name -> {
                        service.device.info().getOrThrow().let {
                            it.displayWidth to it.displayHeight
                        }
                    }
                    TUiautomatorDevice::dumpHierarchy.name -> {
                        val result = service.apiService.dumpHierarchy().unwrap().toString()
                        result
//                        val reader = SAXReader()
//                        val document = reader.read(ByteArrayInputStream(result.toByteArray()))
//                        val rootEle = document.rootElement
//                        val nodes = mutableListOf<TUiautomatorHierarchy.Node>()
//                        if (rootEle.name == "hierarchy") {
//                            val it = rootEle.elementIterator()
//                            while (it.hasNext()) {
//                                parseNode(it.next(), nodes)
//                            }
//                        }
//                        TUiautomatorHierarchy(nodes)
                    }
                    else -> null
                }
            }
        }

    /**
     * 解析节点.
     * @param ele Element
     * @param nodes MutableList<Node>
     */
//    private fun parseNode(
//        ele: Element,
//        nodes: MutableList<TUiautomatorHierarchy.Node>
//    ) {
//        val children = mutableListOf<TUiautomatorHierarchy.Node>()
//        val it = ele.elementIterator()
//        while (it.hasNext()) {
//            parseNode(it.next(), children)
//        }
//        val node = TUiautomatorHierarchy.Node(
//            ele.attribute("index").value.toInt(),
//            ele.attribute("text").value.toString(),
//            ele.attribute("resource-id").value.toString(),
//            ele.attribute("class").value.toString(),
//            ele.attribute("package").value.toString(),
//            ele.attribute("content-desc").value.toString(),
//            ele.attribute("checkable").value.toTBool(),
//            ele.attribute("checked").value.toTBool(),
//            ele.attribute("clickable").value.toTBool(),
//            ele.attribute("enabled").value.toTBool(),
//            ele.attribute("focusable").value.toTBool(),
//            ele.attribute("focused").value.toTBool(),
//            ele.attribute("scrollable").value.toTBool(),
//            ele.attribute("long-clickable").value.toTBool(),
//            ele.attribute("password").value.toTBool(),
//            ele.attribute("selected").value.toTBool(),
//            ele.attribute("visible-to-user").value.toTBool(),
//            ele.attribute("bounds").value.toString().let {
//                TRect().apply {
//                    BOUND_REGEX.findAll(it).forEachIndexed { index, matchResult ->
//                        if (index == 0) {
//                            left = matchResult.groupValues[1].toInt()
//                            top = matchResult.groupValues[2].toInt()
//                        } else if (index == 1) {
//                            right = matchResult.groupValues[1].toInt()
//                            bottom = matchResult.groupValues[2].toInt()
//                        }
//                    }
//                }
//            },
//            children
//        )
//        nodes += node
//    }

    companion object {
        val BOUND_REGEX by lazy { "\\[([0-9]+),([0-9]+)]".toRegex() }
    }
}