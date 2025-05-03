/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.entity.render.ViewRender
import com.yesferal.hornsapp.core.domain.entity.util.LocalizedString
import java.util.*

data class GetConcert(
    val _id: String,
    val name: String?,
    val about: LocalizedString?,
    val dateTime: Date?,
    val totalDays: Int?,
    val headliner: ConcertComponent?,
    val ticketing: ConcertComponent?,
    val links: List<ViewRender>?,
    val categories: List<String>?,
    val venue: GetVenue?,
    val state: GetState?,
    val activities: List<GetBand>?,
    val lineup: GetLineup?,
) {
    fun mapToConcert(): Concert {
        val isFavorite = false

        return Concert.Builder(this._id)
            .addName(this.name)
            .addAbout(this.about?.text)
            .addTimeInMillis(this.dateTime?.time)
            .addTotalDays(this.totalDays)
            .addHeadlinerName(this.headliner?.name)
            .addHeadlinerImageUrl(this.headliner?.url)
            .addTicketingName(this.ticketing?.name)
            .addTicketingUrl(this.ticketing?.url)
            .addLinks(this.links)
            .addCategories(this.categories)
            .addVenue(this.venue?.mapToVenue())
            .addState(this.state?.mapToState())
            .addActivities(this.activities?.map { it.mapToBand() })
            .isFavorite(isFavorite)
            .addLineup(lineup?.mapToLineup())
            .build()
    }
}

data class ConcertComponent(
    val name: String,
    val url: String,
)
