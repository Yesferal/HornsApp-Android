package com.yesferal.hornsapp.app.framework.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(
    private val constants: RetrofitConstants
) {

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(constants.authorization()))
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(constants.baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}