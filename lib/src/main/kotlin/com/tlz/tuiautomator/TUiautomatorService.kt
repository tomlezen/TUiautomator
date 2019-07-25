package com.tlz.tuiautomator

import com.tlz.tuiautomator.annotations.TUiautomatorKeyName
import com.tlz.tuiautomator.i.TUiautomatorKeys
import com.tlz.tuiautomator.net.TUiautomatorApiService
import com.tlz.tuiautomator.net.request.JsonrpcRequest
import com.tlz.tuiautomator.net.request.pressKeyReuest
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Proxy
import java.util.concurrent.TimeUnit

/**
 * 实现与atx-agent服务器通信.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 13:58.
 */
internal class TUiautomatorService(private val config: TUiautomatorConfig) : TUiautomator {

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
            .baseUrl("http:${config.atxAgentIp}:$ATX_AGENT_PORT")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()
    }

    private val apiService by lazy {
        retrofit.create(TUiautomatorApiService::class.java)
    }

    override val keys: TUiautomatorKeys = createTUiautomatorKeys()

    /**
     * 创建TUiautomatorKeys动态代理.
     * @return TUiautomatorKeys
     * @throws Throwable
     */
    private fun createTUiautomatorKeys(): TUiautomatorKeys =
        Proxy.newProxyInstance(
            TUiautomatorKeys::class.java.classLoader, arrayOf(TUiautomatorKeys::class.java)
        ) { _, method, _ ->
            runBlocking {
                runTCatching {
                    // 先查找注解
                    val nameAnnotation = method.getAnnotation(TUiautomatorKeyName::class.java)
                    val name = nameAnnotation?.name ?: method.name
                    pressKeyReuest(name).request().toString().toBoolean()
                }
            }
        } as TUiautomatorKeys

    private suspend fun JsonrpcRequest.request() = apiService.jsonrpc(this).unwrap()

    companion object {
        /** atx-agent默认端口. */
        private const val ATX_AGENT_PORT = "7912"
    }
}