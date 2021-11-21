package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Concert
import java.util.*

data class GetConcerts(
    val _id: String,
    val name: String?,
    val headlinerImage: String?,
    val dateTime: Date?,
    val genre: String?,
    val tags: List<String>?,
    val ticketingUrl: String?,
    val ticketingHost: String?
) {
    fun mapToConcert(): Concert {
        
        val isFavorite = false

        return Concert.Builder(this._id)
            .addName(this.name)
            .addHeadlinerImage(this.headlinerImage)
            .addTimeInMillis(this.dateTime?.time)
            .addGenre(this.genre)
            .addTags(this.tags)
            .addTicketingUrl(this.ticketingUrl)
            .addTicketingHost(this.ticketingHost)
            .isFavorite(isFavorite)
            .build()
    }
}
