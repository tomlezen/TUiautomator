package com.tlz.tuiautomator.net.request

import com.tlz.tuiautomator.TUiautomatorMethods

/**
 * Jsonrpc请求.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 14:29.
 * @property jsonrpc String Jsonrpc版本.
 * @property id String 请求id序号.
 * @property method String 需要执行的方法名.
 * @property params Any? 参数.
 */
data class JsonrpcRequest(
    val jsonrpc: String,
    val id: String,
    val method: String,
    val params: Any?
)

fun jsonrpcRequest(
    jsonrpc: String = "2.0",
    id: String = System.currentTimeMillis().toString(),
    method: String,
    params: Any? = null
) = JsonrpcRequest(jsonrpc, id, method, params)

fun pressKeyReuest(
    name: String
) = jsonrpcRequest(method = TUiautomatorMethods.PRESS_KEY, params = arrayOf(name))

fun scrollToRequest(
    scroll: ScrollRequestParams,
    scrollTo: ScrollToTextRequestParams,
    vararg param: Any
) = jsonrpcRequest(method = "scrollTo", params = param.asList() + scroll + scrollTo)

fun scrollToEndRequest(
    scroll: ScrollRequestParams,
    vararg param: Any
) = jsonrpcRequest(method = "scrollToEnd", params = param.asList() + scroll)

fun scrollToBeginningRequest(
    scroll: ScrollRequestParams,
    vararg param: Any
) = jsonrpcRequest(method = "scrollToBeginning", params = param.asList() + scroll)

fun scrollBackwardRequest(
    scroll: ScrollRequestParams,
    vararg param: Any
) = jsonrpcRequest(method = "scrollBackward", params = param.asList() + scroll)

fun scrollForwardRequest(
    scroll: ScrollRequestParams,
    vararg param: Any
) = jsonrpcRequest(method = "scrollForward", params = param.asList() + scroll)