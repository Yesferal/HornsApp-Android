package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.Concert

class UpcomingViewData(
    id: String,
    name: String?,
    val day: String?,
    val month: String?,
    val ticketingHostName: String?
): ViewData(id, name)

fun Concert.mapToUpcomingView() =
    UpcomingViewData(
        id = this.id,
        day = this.day,
        month = this.month,
        name = this.name,
        ticketingHostName = this.ticketingHost
    )