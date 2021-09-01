package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.data.abstraction.features.FavoriteDataSource
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Concert

class ConcertRepositoryImpl(
    private val favoriteDataSource: FavoriteDataSource,
    private val apiDataSource: ApiDataSource
) : ConcertRepository {
    var concerts: List<Concert>? = null

    override suspend fun getConcerts(): Result<List<Concert>> {
        return concerts?.let {
            Result.Success(it)
        } ?: run {
            val result = apiDataSource.getConcerts()
            if (result is Result.Success) {
                concerts = result.value
            }
            result
        }
    }

    override suspend fun getConcert(
        id: String
    ): Result<Concert> = apiDataSource.getConcert(id)

    override suspend fun getFavoriteConcert(): List<Concert> {
        return favoriteDataSource.getFavoriteConcerts()
    }

    override suspend fun insertFavoriteConcert(
        concert: Concert
    ) {
        favoriteDataSource.insertFavoriteConcert(concert)
    }

    override suspend fun removeFavoriteConcert(
        concert: Concert
    ) {
        favoriteDataSource.removeFavoriteConcert(concert)
    }
}
