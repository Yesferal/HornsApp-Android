package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.domain.entity.*
import com.yesferal.hornsapp.domain.util.*
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
    val genre: String?,
    val tags: List<String>?,
    val venue: Venue?,
    val bands: List<Band>?,
    val ticketingUrl: String?,
    val ticketingHost: String?
)

fun GetConcert.mapToConcert(): Concert {
    val dateTime = this
        .dateTime
        ?.dateTimeFormatted()

    val day = this
        .dateTime
        ?.dayFormatted()

    val month = this
        .dateTime
        ?.monthFormatted()

    val time = this
        .dateTime
        ?.timeFormatted()

    val facebookUrl = socialNetworks?.first()

    val isFavorite = false

    val subGenres = this.bands?.map {
        it.genre
    }?.joinToString(" | ")

    return Concert(
        id = this._id,
        name = this.name,
        description = this.description,
        headlinerImage = this.headlinerImage,
        date = this.dateTime,
        dateTime = dateTime,
        day = day,
        month = month,
        time = time,
        trailerUrl = this.trailerUrl?.toSafeUri(),
        facebookUrl = facebookUrl?.toSafeUri(),
        isFavorite = isFavorite,
        genre = this.genre,
        tags = this.tags,
        ticketingUrl = this.ticketingUrl?.toSafeUri(),
        ticketingHost = this.ticketingHost,
        subGenres = subGenres.toString(),
        state = this.state?.name,
        venue = this.venue,
        bands = this.bands
    )
}