package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.presentation.di.FlavorDataClass

class ApiConstants(flavorDataClass: FlavorDataClass) {
    init {
        System.loadLibrary(flavorDataClass.cFileName)
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