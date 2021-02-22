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
            String(),
            authorization()
    )
    val environments = listOf(
            Pair("Dev", "https://demo8819092.mockable.io/"),
            Pair("Prod", baseUrl())
    )
}