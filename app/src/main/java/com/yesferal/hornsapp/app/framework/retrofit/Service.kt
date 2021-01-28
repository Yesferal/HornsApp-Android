package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.framework.retrofit.entity.GetBand
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcert
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcerts
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("/concert")
    fun getConcerts(): Call<List<GetConcerts>>

    @GET("/concert/{id}")
    suspend fun getConcertBy(
        @Path("id") id: String
    ): Response<GetConcert>

    @GET("/band/{id}")
    fun getBandBy(
        @Path("id") id: String
    ): Call<GetBand>
}