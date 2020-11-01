package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.Concert

class NewestViewData(
    id: String,
    name: String?,
    val day: String?,
    val month: String?,
    val ticketingHostName: String?
): ViewData(id, name)

fun Concert.mapToUpcomingView() =
    NewestViewData(
        id = this.id,
        day = this.day,
        month = this.month,
        name = this.name,
        ticketingHostName = this.ticketingHost
    )