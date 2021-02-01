package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.domain.abstraction.BandRepository

class BandRepositoryImpl(
    private val apiDataSource: ApiDataSource
) : BandRepository {

    override suspend fun getBand(
        id: String
    ) = apiDataSource.getBand(id)
}