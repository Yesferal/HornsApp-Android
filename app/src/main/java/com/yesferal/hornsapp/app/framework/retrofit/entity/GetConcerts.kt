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

        return Concert(
            id = this._id,
            name = this.name,
            headlinerImage = this.headlinerImage,
            timeInMillis = this.dateTime?.time,
            genre = this.genre,
            tags = this.tags,
            ticketingUrl = this.ticketingUrl,
            ticketingHost = this.ticketingHost,
            isFavorite = isFavorite
        )
    }
}