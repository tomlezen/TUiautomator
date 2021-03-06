package com.tlz.tuiautomator.utils

import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.tlz.tuiautomator.annotations.TUiautomatorMethodName
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.coroutines.Continuation

/**
 * By tomlezen.
 * Date: 2019-07-26.
 * Time: 16:27.
 */

fun Any?.toTBool() = this?.toString()?.toBoolean() ?: false

fun Any?.toTInt() = this?.toString()?.toInt() ?: 0
fun Any?.toTFloat(def: Float = .0f) = this?.toString()?.toFloat() ?: def
fun Any?.toTLong(def: Long = 0L) = this?.toString()?.toLong() ?: def

val tGson: Gson = Gson().newBuilder().create()

inline fun <reified T> Any.toObj(): T = tGson.fromJson(this.toString(), T::class.java)

/**
 * 将gson解析出来的LinkedTreeMap转为json字符串.
 * @receiver LinkedTreeMap<*, *>
 * @return String
 */
fun LinkedTreeMap<*, *>.toJson(): String =
    StringBuilder().apply {
        append("{")
        this@toJson.forEach {
            append("\"${it.key}\"")
            append(":")
            val value = it.value
            if (value is LinkedTreeMap<*, *>) {
                append(value.toJson())
            } else {
                append("\"$value\"")
            }
            append(",")
        }
        append("\"@@@@\":\"\"")
        append("}")
    }.toString()

/**
 * 创建代理.
 * @param handler InvocationHandler
 * @return T
 */
inline fun <reified T> newTProxy(handler: InvocationHandler) =
    Proxy.newProxyInstance(
        T::class.java.classLoader,
        arrayOf(T::class.java),
        handler
    ) as T

inline val Method.tMethodName: String
    get() = getAnnotation(TUiautomatorMethodName::class.java)?.name ?: name

/** 过滤掉协程参数. */
fun <T> Iterable<T>.filterT() = this.filter { it !is Continuation<*> }

fun <T> Array<T>.filterT() = this.filter { it !is Continuation<*> }

@UseExperimental(ExperimentalContracts::class)
inline fun Boolean?.yes(block: () -> Unit): Boolean? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (this == true) block()
    return this
}

@UseExperimental(ExperimentalContracts::class)
inline fun Boolean?.no(block: () -> Unit): Boolean? {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
    }
    if (this != true) block()
    return this
}

/**
 * 断言.
 * @receiver Boolean
 */
fun Boolean.assert() {
    assert(this)
}