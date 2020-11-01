package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.Concert

class ConcertViewData(
    id: String,
    name: String?,
    val image: String?,
    val day: String?,
    val month: String?,
    val year: String?,
    val time: String?,
    val genre: String?
): ViewData(id, name)

fun Concert.mapToConcertViewData() =
    ConcertViewData(
        id = this.id,
        image = this.headlinerImage,
        day = this.day,
        month = this.month,
        year = this.year.toString(),
        name = this.name,
        time = this.time,
        genre = this.genre
    )