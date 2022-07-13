/* Copyright © 2022 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.logger

import android.util.Log
import com.yesferal.hornsapp.app.BuildConfig
import com.yesferal.hornsapp.core.domain.abstraction.Logger

/**
 * This class is an Android Logger Adapter/Wrapper
 * also it is a part of the Chain Of Responsibility pattern
 *
 * @author Yesferal
 */
class DebuggerAdapter(private val nextLevelLogger: Logger?): Logger {
    private val TAG = "Logger"

    override fun d(message: String) {
        nextLevelLogger?.d(message)
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }

    override fun e(message: String) {
        nextLevelLogger?.e(message)
        if (BuildConfig.DEBUG) {
            Log.e(TAG, message)
        }
    }
}
