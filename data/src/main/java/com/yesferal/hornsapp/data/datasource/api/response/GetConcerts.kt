package com.yesferal.hornsapp.data.datasource.api.response

import com.yesferal.hornsapp.domain.common.formatted
import com.yesferal.hornsapp.domain.common.timeFormatted
import com.yesferal.hornsapp.domain.entity.Concert
import java.util.*

data class GetConcerts(
    val _id: String,
    val name: String?,
    val description: String?,
    val posterImage: String?,
    val headlinerImage: String?,
    val dateTime: Date?
)

fun GetConcerts.mapToConcert(): Concert {
    val date = this
        .dateTime
        ?.formatted()

    val time = this
        .dateTime
        ?.timeFormatted()

    return Concert(
        this._id,
        this.name,
        this.description,
        this.posterImage,
        this.headlinerImage,
        date,
        time
    )
}