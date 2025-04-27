/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.common.extension

import com.yesferal.hornsapp.app.presentation.ui.home.CarouselViewData
import com.yesferal.hornsapp.app.presentation.ui.home.NewestViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.entity.render.ChildrenRender
import com.yesferal.hornsapp.core.domain.entity.render.ViewRender
import com.yesferal.hornsapp.delegate.abstraction.Delegate

fun ViewRender.getConcertDelegates(
    concerts: List<Concert>,
): List<Delegate> {
    // Children On Demand
    // FIXME: We can rename sort as onDemand or something similar
    var children = concerts
        .filter { this.children?.sort?.contains(it.id) == true }

    return when (this.children?.type) {
        ChildrenRender.Type.CAROUSEL_CARD_VIEW -> {
            if (children.isEmpty()) {
                children = this.mapChildrenConcerts(
                    concerts.reversed(),
                )
            }
            children.map {
                CarouselViewData(
                    id = it.id,
                    name = it.name,
                    time = it.timeInMillis.dateTimeFormatted(),
                    headlinerName = it.headlinerName,
                    headlinerUrl = it.headlinerImageUrl,
                    ticketingName = it.ticketingName,
                    ticketingUrl = it.ticketingUrl,
                )
            }
        }
        ChildrenRender.Type.UPCOMING_CARD_VIEW -> {
            if (children.isEmpty()) {
                children = this.mapChildrenConcerts(
                    concerts.sortedWith(compareBy { it.timeInMillis }),
                )
            }
            children.map {
                NewestViewData(
                    id = it.id,
                    day = it.timeInMillis.dayFormatted(),
                    month = it.timeInMillis.monthFormatted(),
                    name = it.name,
                    ticketingHostName = it.ticketingName
                )
            }
        }
        ChildrenRender.Type.UPCOMING_IMAGE_CARD_VIEW -> {
            if (children.isEmpty()) {
                children = this.mapChildrenConcerts(
                    concerts.sortedWith(compareBy { it.timeInMillis }),
                )
            }
            children.map {
                UpcomingViewData(
                    id = it.id,
                    image = it.headlinerImageUrl,
                    day = it.timeInMillis.dayFormatted(),
                    month = it.timeInMillis.monthFormatted(),
                    year = it.timeInMillis.yearFormatted(),
                    name = it.name,
                    time = it.timeInMillis.timeFormatted(),
                    headlinerName = it.headlinerName
                )
            }
        }
        else -> listOf()
    }
}

fun ViewRender.mapChildrenConcerts(
    concerts: List<Concert>,
): List<Concert> {
    return concerts
        .filter { concert ->
            val events = this.children?.filter?.events
            if (events.isNullOrEmpty()) {
                true
            } else {
                events.any { anyEvent ->
                    concert.id == anyEvent
                }
            }
        }
        .filter { concert ->
            val categories = this.children?.filter?.categories
            if (categories.isNullOrEmpty()) {
                true
            } else {
                categories.any { anyCategory ->
                    concert.categories?.contains(anyCategory) == true
                }
            }
        }
        .take(this.children?.take ?: Int.MAX_VALUE)
}
