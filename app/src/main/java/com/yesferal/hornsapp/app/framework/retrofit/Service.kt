package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.framework.retrofit.model.GetConcerts
import retrofit2.Call
import retrofit2.http.GET

interface Service {
    @GET("/concert")
    fun getConcerts(): Call<List<GetConcerts>>
}