/* Copyright © 2022 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.logger

import com.yesferal.hornsapp.core.domain.abstraction.Logger

/**
 * This is a Logger provider class
 * also it is a part of a Chain Of Responsibility pattern
 *
 * @author Yesferal
 */
object ChainLoggerProvider {
    fun provideLogger(): Logger {
        return DebuggerAdapter(nextLevelLogger = CrashlyticsAdapter())
    }
}
