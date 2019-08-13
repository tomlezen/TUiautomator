package com.tlz.tuiautomator

import com.tlz.tuiautomator.i.TUiautomatorDevice
import com.tlz.tuiautomator.i.TUiautomatorGestures
import com.tlz.tuiautomator.i.TUiautomatorKeys
import com.tlz.tuiautomator.i.TUiautomatorToast
import com.tlz.tuiautomator.net.TUiautomatorApiService
import com.tlz.tuiautomator.net.request.JsonrpcRequest
import com.tlz.tuiautomator.selector.TUiSelector
import com.tlz.tuiautomator.selector.TUiautomatorSelectors
import com.tlz.tuiautomator.selector.TUiautomatorSelectorsObj
import com.tlz.tuiautomator.utils.tGson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 实现与atx-agent服务器通信.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 13:58.
 */
class TUiautomatorService internal constructor(val config: TUiautomatorConfig) : TUiautomator {

    private val okhttp by lazy {
        val timeout = config.httpTimeout.toLong()
        OkHttpClient.Builder()
            .callTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http:${config.atxAgentIp}:${config.atxAgentPort}")
            .addConverterFactory(GsonConverterFactory.create(tGson))
            .client(okhttp)
            .build()
    }

    private val apiService by lazy {
        retrofit.create(TUiautomatorApiService::class.java)
    }

    val tools by lazy { TUiautomatorTools(this) }

    override val device: TUiautomatorDevice = TUiautomatorDevice(this)

    override val toast: TUiautomatorToast = TUiautomatorToast(this)

    override val keys: TUiautomatorKeys = TUiautomatorKeys(this)

    override val gestures: TUiautomatorGestures = TUiautomatorGestures(this)

    override fun selector(selector: TUiSelector.() -> Unit): TUiautomatorSelectors =
        TUiautomatorSelectorsObj(this, TUiSelector().apply(selector))

    suspend infix fun rq(request: JsonrpcRequest) = apiService.jsonrpc(request).unwrap()

    suspend infix fun rqNoUnwrap(request: JsonrpcRequest) = apiService.jsonrpc(request)
}