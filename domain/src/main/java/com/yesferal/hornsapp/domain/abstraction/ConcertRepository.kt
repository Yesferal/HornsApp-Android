package com.yesferal.hornsapp.domain.abstraction

import com.yesferal.hornsapp.domain.entity.Concert

interface ConcertRepository {
    fun getConcerts(
        onSuccess: (concerts: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    )

    fun getConcert(
        id: String,
        onSuccess: (concert: Concert) -> Unit,
        onError: (t: Throwable) -> Unit
    )

    fun getFavoriteConcert(): List<String>?

    fun insertFavoriteConcert(
        concert: Concert,
        onComplete: () -> Unit
    )

    fun removeFavoriteConcert(
        concert: Concert,
        onComplete: () -> Unit
    )
}