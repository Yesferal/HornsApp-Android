package com.yesferal.hornsapp.app.presentation.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

abstract class ViewData(
    val id: String,
    val name: String?
) {
    @Parcelize
    class Parcelabled(
        val id: String,
        val name: String?
    ) : Parcelable

    fun asParcelable(): Parcelabled {
        return Parcelabled(
            id,
            name
        )
    }
}