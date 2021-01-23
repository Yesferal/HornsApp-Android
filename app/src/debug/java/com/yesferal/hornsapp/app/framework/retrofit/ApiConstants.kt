package com.yesferal.hornsapp.app.framework.retrofit

class ApiConstants {
    companion object {
        init {
            System.loadLibrary("hornsapp-lib")
        }
    }

    private external fun authorization(): String
    private external fun baseUrl(): String

    private val mockEnvironment = "MOCK"
    private val productionEnvironment = "PROD"

    val authorizations = listOf(
            Pair(mockEnvironment, String()),
            Pair(productionEnvironment, authorization())
    )
    val environments = listOf(
            Pair(mockEnvironment, "http://demo0511701.mockable.io/"),
            Pair(productionEnvironment, baseUrl())
    )
}