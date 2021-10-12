package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.common.HaDate
import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.util.SafeUri
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
    val genre: String?,
    val tags: List<String>?,
    val venue: GetVenue?,
    val bands: List<GetBand>?,
    val ticketingUrl: String?,
    val ticketingHost: String?
) {
    fun mapToConcert(): Concert {

        val facebookUrl = socialNetworks?.first()

        val isFavorite = false

        return Concert(
            id = this._id,
            name = this.name,
            description = this.description,
            trailerUrl = SafeUri(this.trailerUrl),
            facebookUrl = SafeUri(facebookUrl),
            headlinerImage = this.headlinerImage,
            dateTime = HaDate(this.dateTime?.time),
            genre = this.genre,
            tags = this.tags,
            venue = this.venue?.mapToVenue(),
            bands = this.bands?.map { it.mapToBand() },
            ticketingUrl = SafeUri(this.ticketingUrl),
            ticketingHost = this.ticketingHost,
            isFavorite = isFavorite
        )
    }
}