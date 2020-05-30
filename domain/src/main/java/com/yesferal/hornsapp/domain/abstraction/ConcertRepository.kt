package com.yesferal.hornsapp.domain.abstraction

import com.yesferal.hornsapp.domain.entity.Concert

interface ConcertRepository {
    fun getConcert(
        onSuccess: (entities: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}