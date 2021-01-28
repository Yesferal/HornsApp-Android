package com.yesferal.hornsapp.app.framework.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory(
    private val authorization: String,
    private val baseUrl: String
) {

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(authorization))
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}