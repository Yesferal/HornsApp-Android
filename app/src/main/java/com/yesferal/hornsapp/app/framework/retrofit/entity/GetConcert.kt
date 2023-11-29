/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.entity.drawer.LocalizedString
import java.util.*

data class GetConcert(
    val _id: String,
    val name: String?,
    val about: LocalizedString?,
    val posterImage: String?,
    val trailerUrl: String?,
    val socialNetworks: List<String>?,
    val headlinerImage: String?,
    val dateTime: Date?,
    val totalDays: Int?,
    val genre: String?,
    val tags: List<String>?,
    val venue: GetVenue?,
    val bands: List<GetBand>?,
    val ticketingUrl: String?,
    val ticketingHost: String?
) {
    fun mapToConcert(): Concert {

        val facebookUrl = socialNetworks?.firstOrNull()

        val isFavorite = false

        return Concert.Builder(this._id)
            .addName(this.name)
            .addDescription(this.about?.text)
            .addTrailerUrl(this.trailerUrl)
            .addFacebookUrl(facebookUrl)
            .addHeadlinerImage(this.headlinerImage)
            .addTimeInMillis(this.dateTime?.time)
            .addGenre(this.genre)
            .addTags(this.tags)
            .addVenue(this.venue?.mapToVenue())
            .addBands(this.bands?.map { it.mapToBand() })
            .addTicketingUrl(this.ticketingUrl)
            .addTicketingHost(this.ticketingHost)
            .isFavorite(isFavorite)
            .addTotalDays(this.totalDays)
            .build()
    }
}
