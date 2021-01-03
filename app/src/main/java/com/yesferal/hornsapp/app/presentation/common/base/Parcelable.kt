package com.yesferal.hornsapp.app.presentation.common.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

interface Parcelable {
    fun asParcelable(): ParcelableViewData
}

@Parcelize
class ParcelableViewData(
    val id: String,
    val name: String?
) : Parcelable