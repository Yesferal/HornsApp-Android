package com.yesferal.hornsapp.app.framework.logger

import android.util.Log
import com.yesferal.hornsapp.domain.abstraction.Logger

object YLogger: Logger {
    private const val TAG = "YLogger"

    override fun d(message: String) {
        Log.d(TAG, message)
    }

    override fun e(message: String) {
        Log.e(TAG, message)
    }
}
