package com.yesferal.hornsapp.app.presentation.concert

import android.os.Parcelable
import com.yesferal.hornsapp.domain.entity.Concert
import kotlinx.android.parcel.Parcelize

@Parcelize
class ConcertParcelable(
    val id: String,
    val name: String?,
    val posterImage: String?,
    var isFavorite: Boolean
) : Parcelable

fun Concert.asParcelable(): ConcertParcelable {
    return ConcertParcelable(
        id,
        name,
        posterImage,
        isFavorite
    )
}