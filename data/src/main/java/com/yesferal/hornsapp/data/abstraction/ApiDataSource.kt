package com.yesferal.hornsapp.data.abstraction

import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Concert

interface ApiDataSource {
    fun getConcerts(
        onSuccess: (entity: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    )

    fun getConcert(
        id: String,
        onSuccess: (entity: Concert) -> Unit,
        onError: (t: Throwable) -> Unit
    )

    fun getBand(
        id: String,
        onSuccess: (entity: Band) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}