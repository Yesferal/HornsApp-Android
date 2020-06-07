package com.yesferal.hornsapp.data.mapper

import com.yesferal.hornsapp.data.repository.entity.GetConcerts
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.common.formatted
import com.yesferal.hornsapp.domain.common.timeFormatted

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