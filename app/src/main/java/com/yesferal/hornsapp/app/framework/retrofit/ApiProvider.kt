package com.yesferal.hornsapp.app.framework.retrofit

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiProvider private constructor(
    private val interceptors: List<Interceptor>,
    private val baseUrl: String,
    private val gson: Gson?
) {
    enum class ApiFramework {
        CONTENT_RETROFIT
    }

    private val client by lazy {
        val builder = OkHttpClient.Builder()
        interceptors.forEach {
            builder.addInterceptor(it)
        }

        builder.build()
    }

    private val contentRetrofit: Retrofit by lazy {
        val builder = Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(client)
        }

        gson?.let {
            builder.addConverterFactory(GsonConverterFactory.create(gson))
        }

        builder.build()
    }

    fun getFramework(apiFramework: ApiFramework): Retrofit {
        return when(apiFramework) {
            ApiFramework.CONTENT_RETROFIT -> contentRetrofit
        }
    }

    class Builder {
        private val interceptors: MutableList<Interceptor> = mutableListOf()
        private var baseUrl: String = ""
        private var gson: Gson? = null

        fun addInterceptors(interceptors: List<Interceptor>) = apply {
            this.interceptors.addAll(interceptors)
        }

        fun addBaseUrl(baseUrl: String) = apply {
            this.baseUrl = baseUrl
        }

        fun addConverter(gson: Gson) = apply {
            this.gson = gson
        }

        fun build(): ApiProvider {
            return ApiProvider(interceptors, baseUrl, gson)
        }
    }
}
