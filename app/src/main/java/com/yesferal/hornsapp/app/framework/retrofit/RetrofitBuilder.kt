package com.yesferal.hornsapp.app.framework.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {

    companion object {
        init {
            System.loadLibrary("hornsapp-lib")
        }
    }

    private external fun authorization(): String
    private external fun baseUrl(): String

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(authorization()))
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun createService(): Service {
        return retrofit.create(Service::class.java)
    }
}