package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.data.abstraction.OrmDataSource
import com.yesferal.hornsapp.domain.entity.Concert

class ConcertRepositoryImpl(
    private val ormDataSource: OrmDataSource,
    private val apiDataSource: ApiDataSource
) : ConcertRepository {

    override fun getConcerts(
        onSuccess: (concerts: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        apiDataSource.getConcerts(
            onSuccess = {
                onSuccess(it)
            },
            onError = {
                onError(it)
            }
        )
    }

    override fun getConcert(
        id: String,
        onSuccess: (concert: Concert) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        apiDataSource.getConcert(
            id,
            onSuccess = {
                onSuccess(it)
            },
            onError = {
                onError(it)
            }
        )
    }

    override suspend fun getFavoriteConcert(): List<Concert> {
        return ormDataSource.getFavoriteConcerts()
    }

    override suspend fun insertFavoriteConcert(
        concert: Concert
    ) {
        ormDataSource.insertFavoriteConcert(concert)
    }

    override suspend fun removeFavoriteConcert(
        concert: Concert
    ) {
        ormDataSource.removeFavoriteConcert(concert)
    }
}