package com.tlz.tuiautomator.net

import com.tlz.tuiautomator.net.model.AppInfoResult
import com.tlz.tuiautomator.net.model.JsonrpcResult
import com.tlz.tuiautomator.net.model.SessionResult
import com.tlz.tuiautomator.net.model.ShellCmdResult
import com.tlz.tuiautomator.net.request.JsonrpcRequest
import retrofit2.Response
import retrofit2.http.*

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

    /**
     * shell命令.
     * @param command String
     * @param timeout Long
     * @return ShellCmdResult
     */
    @POST("/shell")
    @FormUrlEncoded
    suspend fun shell(
        @Field(
            "command",
            encoded = true
        ) command: String, @Field("timeout") timeout: Long
    ): ShellCmdResult

    /**
     * 建立session.
     * @param pkgName String
     * @param flags Array<out String>
     * @return SessionResult
     */
    @POST("/session/{pkgName}")
    @FormUrlEncoded
    suspend fun session(@Path("pkgName") pkgName: String, @Field("flags") vararg flags: String): SessionResult

    /**
     * 获取应用信息。
     * @param pkgName String
     * @return AppInfoResult
     */
    @GET("/packages/{pkgName}/info")
    suspend fun appInfo(@Path("pkgName") pkgName: String): AppInfoResult
}