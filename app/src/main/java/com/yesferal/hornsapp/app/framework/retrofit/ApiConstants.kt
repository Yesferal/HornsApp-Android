package com.yesferal.hornsapp.app.framework.retrofit

import com.yesferal.hornsapp.app.presentation.di.SettingFlavorDataClass

class ApiConstants(settingFlavorDataClass: SettingFlavorDataClass) {
    init {
        System.loadLibrary(settingFlavorDataClass.cFileName)
    }

    private external fun authorization(): String
    private external fun baseUrl(): String

    val authorizations = listOf(
        authorization()
    )
    val environments = listOf(
        Pair("Prod", baseUrl())
    )
}
