package com.yesferal.hornsapp.app.framework.retrofit

class ApiConstants {
    companion object {
        init {
            System.loadLibrary("hornsapp-lib")
        }
    }

    private external fun authorization(): String
    private external fun baseUrl(): String

    private val productionEnvironment = "PROD"

    val authorizations = listOf(
        Pair(productionEnvironment, authorization())
    )
    val environments = listOf(
        Pair(productionEnvironment, baseUrl())
    )
}