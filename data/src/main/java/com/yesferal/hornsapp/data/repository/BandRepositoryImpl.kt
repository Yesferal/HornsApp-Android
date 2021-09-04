package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.data.abstraction.remote.BandRemoteDataSource
import com.yesferal.hornsapp.domain.abstraction.BandRepository

class BandRepositoryImpl(
    private val bandRemoteDataSource: BandRemoteDataSource
) : BandRepository {

    override suspend fun getBand(
        id: String
    ) = bandRemoteDataSource.getBand(id)
}
