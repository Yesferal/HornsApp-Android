package com.yesferal.hornsapp.app.framework.retrofit

class ApiConstants {
    companion object {
        init {
            System.loadLibrary("hornsapp-lib")
        }
    }

    external fun authorization(): String
    external fun baseUrl(): String
}