package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.domain.entity.*
import com.yesferal.hornsapp.domain.util.datetimeFormatted
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
    val category: String?,
    val local: Local?,
    val bands: List<Band>?,
    val ticketingUrl: String?,
    val ticketingHost: String?
)

fun GetConcert.mapToConcert(): Concert {
    val datetime = this
        .dateTime
        ?.datetimeFormatted()

    val facebookUrl = socialNetworks?.first()

    val isFavorite = false

    val genres = this.bands?.map {
        it.genre
    }?.joinToString(" | ")

    return Concert(
        this._id,
        this.name,
        this.description,
        this.posterImage,
        this.headlinerImage,
        datetime,
        this.trailerUrl?.toSafeUri(),
        facebookUrl?.toSafeUri(),
        isFavorite,
        category?: CategoryKey.LIVE.toString(),
        genres = genres.toString(),
        state = this.state?.name,
        local = this.local,
        bands = this.bands,
        ticketingUrl = this.ticketingUrl?.toSafeUri(),
        ticketingHost = this.ticketingHost
    )
}