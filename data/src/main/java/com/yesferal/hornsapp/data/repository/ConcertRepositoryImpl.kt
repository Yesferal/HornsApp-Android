package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.data.abstraction.StorageDataSource
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.entity.Concert

class ConcertRepositoryImpl(
    private val storageDataSource: StorageDataSource,
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

    override fun insertConcerts(concerts: List<Concert>?) {
        storageDataSource.insertConcerts(concerts)
    }

    override fun getConcertsFromStorage(): List<Concert>?  {
        return storageDataSource.getConcerts()
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

    override fun getFavoriteConcert(): List<String>? {
        return storageDataSource.getFavoriteConcerts()
    }

    override fun insertFavoriteConcert(
        concert: Concert,
        onComplete: () -> Unit
    ) {
        storageDataSource.insertFavoriteConcert(concert) {
            onComplete()
        }
    }

    override fun removeFavoriteConcert(
        concert: Concert,
        onComplete: () -> Unit
    ) {
        storageDataSource.removeFavoriteConcert(concert) {
            onComplete()
        }
    }
}