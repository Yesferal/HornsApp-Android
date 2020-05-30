package com.yesferal.hornsapp.domain.repository

import com.yesferal.hornsapp.domain.entity.Concert

interface ConcertRepository {
    fun getConcert(
        onSuccess: (entities: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}