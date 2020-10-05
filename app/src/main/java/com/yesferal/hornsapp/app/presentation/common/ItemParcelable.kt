package com.yesferal.hornsapp.app.presentation.common

import android.os.Parcelable
import com.yesferal.hornsapp.app.presentation.item.adapter.Item
import com.yesferal.hornsapp.domain.entity.Concert
import kotlinx.android.parcel.Parcelize

@Parcelize
class ItemParcelable(
    val id: String,
    val name: String?,
    var isFavorite: Boolean
) : Parcelable

fun Concert.asParcelable(): ItemParcelable {
    return ItemParcelable(
        id,
        name,
        isFavorite
    )
}

fun Item.asParcelable(): ItemParcelable {
    return ItemParcelable(
        id,
        name,
        isFavorite
    )
}