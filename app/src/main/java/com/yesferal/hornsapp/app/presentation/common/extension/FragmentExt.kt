package com.yesferal.hornsapp.app.presentation.common.extension

import android.os.Handler
import android.os.Looper

fun postDelayed(delayMillis: Long = 250, func: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(func, delayMillis)
}
