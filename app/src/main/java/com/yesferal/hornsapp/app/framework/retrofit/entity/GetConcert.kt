package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.entity.State
import com.yesferal.hornsapp.domain.util.formatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import java.util.*

data class GetConcert(
    val _id: String,
    val name: String?,
    val description: String?,
    val posterImage: String?,
    val headlinerImage: String?,
    val dateTime: Date?,
    val state: State?,
    val bands: List<Band>?,
    val socialNetworks: List<String>?
)

fun GetConcert.mapToConcert(): Concert {
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
        time,
        state?.name,
        bands,
        socialNetworks
    )
}