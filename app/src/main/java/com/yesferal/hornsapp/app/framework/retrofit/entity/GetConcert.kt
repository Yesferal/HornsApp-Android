package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.entity.Local
import com.yesferal.hornsapp.domain.entity.State
import com.yesferal.hornsapp.domain.util.day
import com.yesferal.hornsapp.domain.util.month
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.toSafeUri
import java.util.*

data class GetConcert(
    val _id: String,
    val name: String?,
    val description: String?,
    val posterImage: String?,
    val trailerUrl: String?,
    val socialNetworks: List<String>?,
    val headlinerImage: String?,
    val dateTime: Date?,
    val state: State?,
    val local: Local,
    val bands: List<Band>?
)

fun GetConcert.mapToConcert(): Concert {
    val day = this
        .dateTime
        ?.day()

    val month = this
        .dateTime
        ?.month()

    val time = this
        .dateTime
        ?.timeFormatted()

    val facebookUrl = socialNetworks?.first()

    val isFavorite = false

    return Concert(
        this._id,
        this.name,
        this.description,
        this.posterImage,
        this.headlinerImage,
        day,
        month,
        time,
        this.trailerUrl?.toSafeUri(),
        facebookUrl?.toSafeUri(),
        isFavorite,
        this.state?.name,
        this.local,
        this.bands
    )
}