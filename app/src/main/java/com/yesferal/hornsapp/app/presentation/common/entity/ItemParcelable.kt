package com.yesferal.hornsapp.app.presentation.common.entity

import android.os.Parcelable
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.common.adapter.Item
import kotlinx.android.parcel.Parcelize

@Parcelize
class ItemParcelable(
    val id: String,
    val name: String?
) : Parcelable

fun ViewData.asParcelable(): ItemParcelable {
    return ItemParcelable(
        id,
        name
    )
}

fun Item.asParcelable(): ItemParcelable {
    return ItemParcelable(
        id,
        name
    )
}