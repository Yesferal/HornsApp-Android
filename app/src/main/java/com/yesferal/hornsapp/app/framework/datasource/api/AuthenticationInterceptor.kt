package com.yesferal.hornsapp.app.framework.datasource.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
    private val authorization: String
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", authorization)
            .build()

        return chain.proceed(newRequest)
    }
}