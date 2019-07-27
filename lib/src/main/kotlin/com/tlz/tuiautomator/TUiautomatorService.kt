package com.tlz.tuiautomator

import com.tlz.tuiautomator.i.TUiautomatorGestures
import com.tlz.tuiautomator.i.TUiautomatorKeys
import com.tlz.tuiautomator.net.TUiautomatorApiService
import com.tlz.tuiautomator.net.request.JsonrpcRequest
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
        OkHttpClient.Builder()
            .callTimeout(config.waitTimeout.toLong(), TimeUnit.SECONDS)
            .readTimeout(config.waitTimeout.toLong(), TimeUnit.SECONDS)
            .connectTimeout(config.waitTimeout.toLong(), TimeUnit.SECONDS)
            .writeTimeout(config.waitTimeout.toLong(), TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http:${config.atxAgentIp}:${config.atxAgentPort}")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()
    }

    private val apiService by lazy {
        retrofit.create(TUiautomatorApiService::class.java)
    }

    override val keys: TUiautomatorKeys = TUiautomatorKeys(this)

    override val gestures: TUiautomatorGestures = TUiautomatorGestures(this)

    suspend infix fun rq(request: JsonrpcRequest) = apiService.jsonrpc(request).unwrap()
}