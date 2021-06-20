package com.yesferal.hornsapp.app.framework.retrofit

class ApiConstants {
    companion object {
        init {
            System.loadLibrary("hornsapp-lib")
        }
    }

    private external fun authorization(): String
    private external fun baseUrl(): String

    val authorizations = listOf(
        authorization()
    )
    val environments = listOf(
        Pair("R", baseUrl())
    )
}