package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcert
import com.yesferal.hornsapp.app.framework.retrofit.entity.GetConcerts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("/concert")
    fun getConcerts(): Call<List<GetConcerts>>

    @GET("/concert/{id}")
    fun getConcertBy(
        @Path("id") id: String
    ): Call<GetConcert>
}