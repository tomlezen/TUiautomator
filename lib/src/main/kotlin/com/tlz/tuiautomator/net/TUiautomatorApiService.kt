package com.tlz.tuiautomator.net

import com.tlz.tuiautomator.net.model.JsonrpcResult
import com.tlz.tuiautomator.net.model.TDeviceInfo
import com.tlz.tuiautomator.net.request.JsonrpcRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * api服务.
 * By tomlezen.
 * Date: 2019-07-25.
 * Time: 14:22.
 */
interface TUiautomatorApiService {
    /**
     * jsonrpc请求.
     * @param body JsonrpcRequest
     * @return JsonrpcResult
     */
    @POST("/jsonrpc/0")
    suspend fun jsonrpc(@Body body: JsonrpcRequest): JsonrpcResult

    /**
     * 获取界面层级.
     * @return JsonrpcResult
     */
    @GET("/dump/hierarchy")
    suspend fun hierarchy(): JsonrpcResult

    /**
     * 截取请求.
     * @return Response<Body>
     */
    @GET("/screenshot/0")
    suspend fun screenshot(): Response<Body>
}