/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.common.extension

import com.google.gson.Gson
import com.yesferal.hornsapp.app.presentation.common.base.ParcelableViewData
import com.yesferal.hornsapp.core.domain.navigator.Parameters

fun Parameters?.getParcelableViewData(key: String): ParcelableViewData? {
    return this?.get<ParcelableViewData>(key)
        ?: try {
            val json = this?.parameters?.get(key).toString()
            val parcelableViewDataFromString =
                Gson().fromJson(json, ParcelableViewData::class.java)
            parcelableViewDataFromString
        } catch (e: Exception) {
            null
        }
}
