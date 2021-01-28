package com.yesferal.hornsapp.domain.abstraction

import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Concert

interface ConcertRepository {
    fun getConcerts(
        onSuccess: (concerts: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    )

    suspend fun getConcert(
        id: String
    ): Result<Concert>

    suspend fun getFavoriteConcert(): List<Concert>

    suspend fun insertFavoriteConcert(
        concert: Concert
    )

    suspend fun removeFavoriteConcert(
        concert: Concert
    )
}