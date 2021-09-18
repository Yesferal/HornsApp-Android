package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.data.abstraction.storage.ConcertStorageDataSource
import com.yesferal.hornsapp.data.abstraction.remote.ConcertRemoteDataSource
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Concert

class ConcertRepositoryImpl(
    private val concertStorageDataSource: ConcertStorageDataSource,
    private val concertRemoteDataSource: ConcertRemoteDataSource
) : ConcertRepository {
    var concerts: List<Concert>? = null

    override suspend fun getConcerts(): Result<List<Concert>> {
        return concerts?.let {
            Result.Success(it)
        } ?: run {
            val result = concertRemoteDataSource.getConcerts()
            if (result is Result.Success) {
                concerts = result.value
            }
            result
        }
    }

    override suspend fun getConcert(
        id: String
    ): Result<Concert> = concertRemoteDataSource.getConcert(id)

    override suspend fun getFavoriteConcert(): List<Concert> {
        return concertStorageDataSource.getFavoriteConcerts()
    }

    override suspend fun insertFavoriteConcert(
        concert: Concert
    ) {
        concertStorageDataSource.insertFavoriteConcert(concert)
    }

    override suspend fun removeFavoriteConcert(
        concert: Concert
    ) {
        concertStorageDataSource.removeFavoriteConcert(concert)
    }
}
