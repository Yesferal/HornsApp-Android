/* Copyright © 2022 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.logger

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.yesferal.hornsapp.app.BuildConfig
import com.yesferal.hornsapp.core.domain.abstraction.Logger

/**
 * This class is a Firebase Crashlytics Adapter/Wrapper
 * also it is a part of a Chain Of Responsibility pattern
 *
 * @author Yesferal
 */
class CrashlyticsAdapter(private val nextLevelLogger: Logger? = null): Logger {

    override fun d(message: String) {
        nextLevelLogger?.d(message)
    }

    override fun e(message: String) {
        nextLevelLogger?.e(message)
        if (!BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().log(message)
        }
    }
}
