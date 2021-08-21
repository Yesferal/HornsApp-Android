package com.yesferal.hornsapp.app.framework.logger

import android.util.Log

object YLog {
    private const val TAG = "YLog"

    fun d(message: String) {
        Log.d(TAG, message)
    }
}
