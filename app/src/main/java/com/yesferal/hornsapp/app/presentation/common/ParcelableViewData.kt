package com.yesferal.hornsapp.app.presentation.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ParcelableViewData(
    val id: String,
    val name: String?
) : Parcelable