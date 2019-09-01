package com.tlz.tuiautomator

import com.tlz.tuiautomator.i.*
import com.tlz.tuiautomator.net.TUiautomatorApiService
import com.tlz.tuiautomator.net.request.JsonrpcRequest
import com.tlz.tuiautomator.selector.TUiSelector
import com.tlz.tuiautomator.selector.TUiautomatorSelectors
import com.tlz.tuiautomator.selector.TUiautomatorSelectorsObj
import com.tlz.tuiautomator.step.TUiautomatorStep
import com.tlz.tuiautomator.step.TUiautomatorStepsTask
import com.tlz.tuiautomator.step.impl.TUiautomatorStepsTaskImpl
import com.tlz.tuiautomator.utils.tGson
import com.tlz.tuiautomator.xpath.TUiautomatorXpath
import com.tlz.tuiautomator.xpath.TUiautomatorXpathObj
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

    val apiService: TUiautomatorApiService by lazy {
        retrofit.create(TUiautomatorApiService::class.java)
    }

    val tools by lazy { TUiautomatorTools(this) }

    override val device: TUiautomatorDevice = TUiautomatorDevice(this)

    override val toast: TUiautomatorToast = TUiautomatorToast(this)

    override val keys: TUiautomatorKeys = TUiautomatorKeys(this)

    override val gestures: TUiautomatorGestures = TUiautomatorGestures(this)

    override val shell: TUiautomatorShell = TUiautomatorShell(this)

    override val application: TUiautomatorApplication = TUiautomatorApplication(this)

    override fun selector(selector: TUiSelector.() -> Unit): TUiautomatorSelectors =
        TUiautomatorSelectorsObj(this, TUiSelector().apply(selector))

    override fun xpath(xpath: String): TUiautomatorXpath = TUiautomatorXpathObj(this, xpath)

    override fun createTask(isTestMode: Boolean): TUiautomatorStepsTask = TUiautomatorStepsTaskImpl(isTestMode)

    override fun createStep(step: suspend (task: TUiautomatorStepsTask) -> Int?): TUiautomatorStep =
        object : TUiautomatorStep(this) {
            override suspend fun run(task: TUiautomatorStepsTask): Int? = step.invoke(task)
        }

    suspend infix fun rq(request: JsonrpcRequest) = apiService.jsonrpc(request).unwrap()

    suspend infix fun rqNoUnwrap(request: JsonrpcRequest) = apiService.jsonrpc(request)
}