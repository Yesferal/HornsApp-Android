package com.yesferal.hornsapp.data.abstraction

import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Concert

interface ApiDataSource {
    fun getConcerts(
        onSuccess: (entity: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    )

    suspend fun getConcert(
        id: String
    ): Result<Concert>

    fun getBand(
        id: String,
        onSuccess: (entity: Band) -> Unit,
        onError: (t: Throwable) -> Unit
    )
}