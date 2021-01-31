package com.yesferal.hornsapp.data.abstraction

import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Concert

interface ApiDataSource {
    suspend fun getConcerts(): Result<List<Concert>>

    suspend fun getConcert(
        id: String
    ): Result<Concert>

    suspend fun getBand(
        id: String
    ): Result<Band>
}