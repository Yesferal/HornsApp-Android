package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.framework.retrofit.entity.GetBand
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcert
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcerts
import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("/concert")
    suspend fun getConcerts(): Response<List<GetConcerts>>

    @GET("/concert/{id}")
    suspend fun getConcertBy(
        @Path("id") id: String
    ): Response<GetConcert>

    @GET("/band/{id}")
    suspend fun getBandBy(
        @Path("id") id: String
    ): Response<GetBand>

    @GET("/drawer")
    suspend fun getAppDrawer(): Response<AppDrawer>
}
