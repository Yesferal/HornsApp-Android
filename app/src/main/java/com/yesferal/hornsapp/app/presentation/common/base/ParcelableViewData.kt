package com.yesferal.hornsapp.app.presentation.common.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



interface Parcelable {

    @Parcelize
    class ViewData(
        val id: String,
        val name: String?
    ) : Parcelable

    fun asParcelable(): ViewData
}