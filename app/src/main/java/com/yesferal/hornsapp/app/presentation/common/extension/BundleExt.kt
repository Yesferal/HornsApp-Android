/* Copyright © 2025 Yesferal Cueva. All rights reserved. */

package com.yesferal.hornsapp.app.presentation.common.extension

import android.os.Build
import android.os.Bundle
import com.yesferal.hornsapp.app.framework.navigator.ParcelableViewData

fun Bundle?.getParcelableViewData(KEY: String): ParcelableViewData? {
    return if (Build.VERSION.SDK_INT >= 33) {
        this?.getParcelable(
            KEY,
            ParcelableViewData::class.java
        ) ?: return null
    } else {
        this?.getParcelable(
            KEY
        ) ?: return null
    }
}
