package com.yesferal.hornsapp.app.framework.logger

import android.util.Log
import com.yesferal.hornsapp.app.BuildConfig
import com.yesferal.hornsapp.core.domain.abstraction.Logger

object YLogger: Logger {
    private const val TAG = "YLogger"

    override fun d(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }

    override fun e(message: String) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, message)
        }
    }
}
