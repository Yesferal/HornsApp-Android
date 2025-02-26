/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.entity.drawer.LocalizedString
import java.util.*

data class GetConcert(
    val _id: String,
    val name: String?,
    val about: LocalizedString?,
    val dateTime: Date?,
    val headliner: ConcertComponent?,
    val ticketing: ConcertComponent?,
    val totalDays: Int?,
    val links: List<ConcertLinks>?,
    val tags: List<String>?,
    val venue: GetVenue?,
    val state: GetState?,
    val bands: List<GetBand>?,
) {
    fun mapToConcert(): Concert {
        val isFavorite = false

        return Concert.Builder(this._id)
            .addName(this.name)
            .addDescription(this.about?.text)
            .addTimeInMillis(this.dateTime?.time)
            .addGenre(this.headliner?.name)
            .addHeadlinerImage(this.headliner?.url)
            .addTicketingHost(this.ticketing?.name)
            .addTicketingUrl(this.ticketing?.url)
            .addTotalDays(this.totalDays)
            .addTags(this.tags)
            .addVenue(this.venue?.mapToVenue())
            .addBands(this.bands?.map { it.mapToBand() })
            .isFavorite(isFavorite)
            .build()
    }
}

class ConcertComponent (
    val name: String,
    val url: String,
)

class ConcertLinks (
    val key: String,
    val name: LocalizedString,
    val icon: String,
    val url: String,
)
