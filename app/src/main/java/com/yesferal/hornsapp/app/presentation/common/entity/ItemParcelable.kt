package com.yesferal.hornsapp.app.presentation.common.entity

import android.os.Parcelable
import com.yesferal.hornsapp.app.presentation.common.adapter.Item
import com.yesferal.hornsapp.domain.entity.Concert
import kotlinx.android.parcel.Parcelize

@Parcelize
class ItemParcelable(
    val id: String,
    val name: String?,
    val imageUrl: String?
) : Parcelable

fun Concert.asParcelable(): ItemParcelable {
    return ItemParcelable(
        id,
        name,
        headlinerImage
    )
}

fun Item.asParcelable(): ItemParcelable {
    return ItemParcelable(
        id,
        name,
        imageUrl
    )
}