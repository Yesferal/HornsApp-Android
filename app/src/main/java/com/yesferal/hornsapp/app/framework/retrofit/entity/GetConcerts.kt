/* Copyright © 2025 Yesferal Cueva. All rights reserved. */

package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Concert
import java.util.*

data class GetConcerts(
    val _id: String,
    val name: String?,
    val dateTime: Date?,
    val headliner: ConcertComponent?,
    val ticketing: ConcertComponent?,
    val tags: List<String>?,
) {
    fun mapToConcert(): Concert {
        
        val isFavorite = false

        return Concert.Builder(this._id)
            .addName(this.name)
            .addTimeInMillis(this.dateTime?.time)
            .addGenre(this.headliner?.name)
            .addHeadlinerImage(this.headliner?.url)
            .addTicketingHost(this.ticketing?.name)
            .addTicketingUrl(this.ticketing?.url)
            .addTags(this.tags)
            .isFavorite(isFavorite)
            .build()
    }
}
