package com.yesferal.hornsapp.app.presentation.common.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ItemParcelable(
    val id: String,
    val name: String?
) : Parcelable