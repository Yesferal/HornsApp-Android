package com.yesferal.hornsapp.app.framework.retrofit

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory(
    private val authorization: String,
    private val baseUrl: String,
    private val gson: Gson
) {

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(authorization))
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }
}