/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.framework.logger.ChainLoggerProvider
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetBand
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcert
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcerts
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetLineup
import com.yesferal.hornsapp.core.domain.entity.render.AppRender
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import com.yesferal.hornsapp.core.domain.util.HaResult
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.lang.Exception

interface Service {
    @GET()
    suspend fun getConcerts(@Url url: String): Response<List<GetConcerts>>

    @GET("/event/{id}")
    suspend fun getConcertBy(
        @Path("id") id: String
    ): Response<GetConcert>

    @GET("/activity/{id}")
    suspend fun getBandBy(
        @Path("id") id: String
    ): Response<GetBand>

    @GET("/screen/{id}")
    suspend fun getScreenRenderBy(
        @Path("id") id: String
    ): Response<ScreenRender>

    @GET("/lineup/{id}")
    suspend fun getLineupBy(
        @Path("id") id: String
    ): Response<GetLineup>

    @GET("app_render")
    suspend fun getAppRender(
        @Query("platform") platform: String,
        @Query("appVersion") appVersion: Long,
        @Query("appId") appId: String
    ): Response<AppRender>
}

suspend fun <INPUT> Service.safeCall(
    request: suspend Service.() -> Response<INPUT>
): Response<INPUT> {
    return try {
        request()
    } catch (e: Exception) {
        ChainLoggerProvider.provideLogger().d("Retrofit: safeCall: e: ${e}")
        Response.error(404, ResponseBody.create(null, String()))
    }
}

fun <INPUT, OUTPUT> Response<INPUT>.mapToResult(
    func: Response<INPUT>.(INPUT) -> OUTPUT
): HaResult<OUTPUT> {
    return if (isSuccessful) {
        body()?.let {
            HaResult.Success(func(it))
        } ?: HaResult.Error
    } else {
        HaResult.Error
    }
}
