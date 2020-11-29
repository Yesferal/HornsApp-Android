package com.yesferal.hornsapp.app.framework.retrofit

class RetrofitConstants {
    companion object {
        init {
            System.loadLibrary("hornsapp-lib")
        }
    }

    external fun authorization(): String
    external fun baseUrl(): String
}