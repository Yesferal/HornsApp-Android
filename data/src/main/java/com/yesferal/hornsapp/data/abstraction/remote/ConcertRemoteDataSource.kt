package com.yesferal.hornsapp.data.abstraction.remote

import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Concert

interface ConcertRemoteDataSource {
    suspend fun getConcerts(): Result<List<Concert>>
    suspend fun getConcert(id: String): Result<Concert>
}
