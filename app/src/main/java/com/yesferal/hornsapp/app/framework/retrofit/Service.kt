/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.framework.retrofit.entity.GetBand
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcert
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcerts
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetLineup
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface Service {
    @GET()
    suspend fun getConcerts(@Url url: String): Response<List<GetConcerts>>

    @GET("/event/{id}")
    suspend fun getConcertBy(
        @Path("id") id: String
    ): Response<GetConcert>

    @GET("/band/{id}")
    suspend fun getBandBy(
        @Path("id") id: String
    ): Response<GetBand>

    @GET("/screen_render/{id}")
    suspend fun getScreenRenderBy(
        @Path("id") id: String
    ): Response<ScreenRender>

    @GET("/lineup/{id}")
    suspend fun getLineupBy(
        @Path("id") id: String
    ): Response<GetLineup>
}
