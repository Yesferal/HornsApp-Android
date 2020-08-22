package com.yesferal.hornsapp.app.presentation.concert

import android.os.Parcelable
import com.yesferal.hornsapp.app.presentation.common.adapter.HornsAppItem
import com.yesferal.hornsapp.domain.entity.Concert
import kotlinx.android.parcel.Parcelize

@Parcelize
class ItemParcelable(
    val id: String,
    val name: String?,
    val posterImage: String?,
    var isFavorite: Boolean
) : Parcelable

fun Concert.asParcelable(): ItemParcelable {
    return ItemParcelable(
        id,
        name,
        posterImage,
        isFavorite
    )
}

fun HornsAppItem.asParcelable(): ItemParcelable {
    return ItemParcelable(
        id,
        name,
        imageUrl,
        isFavorite
    )
}