package com.yesferal.hornsapp.app.framework.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory(
    private val constants: ApiConstants,
    private val environment: Int
) {

    private val client by lazy {
        val authorization = constants.authorizations[environment].second
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(authorization))
            .build()
    }

    val retrofit: Retrofit by lazy {
        val baseUrl = constants.environments[environment].second
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}