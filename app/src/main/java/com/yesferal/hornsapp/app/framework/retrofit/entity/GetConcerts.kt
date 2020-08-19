package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.util.*
import java.util.*

data class GetConcerts(
    val _id: String,
    val name: String?,
    val description: String?,
    val posterImage: String?,
    val trailerUrl: String?,
    val socialNetworks: List<String>?,
    val headlinerImage: String?,
    val dateTime: Date?
)

fun GetConcerts.mapToConcert(): Concert {
    val datetime = this
        .dateTime
        ?.datetimeFormatted()

    val facebookUrl = socialNetworks?.first()

    val isFavorite = false

    return Concert(
        this._id,
        this.name,
        this.description,
        this.posterImage,
        this.headlinerImage,
        datetime,
        this.trailerUrl?.toSafeUri(),
        facebookUrl?.toSafeUri(),
        isFavorite
    )
}