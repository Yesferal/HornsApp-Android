package com.yesferal.hornsapp.app.framework.room

import com.yesferal.hornsapp.app.framework.room.entity.RoomConcert
import com.yesferal.hornsapp.core.data.abstraction.storage.ConcertStorageDataSource
import com.yesferal.hornsapp.core.domain.entity.Concert

class RoomDataSource(
    private val concertDao: ConcertDao
) : ConcertStorageDataSource {

    private var concertsCached: List<Concert>? = null

    override suspend fun insertFavoriteConcert(
        concert: Concert
    ) {
        concertDao.insert(
            RoomConcert(
                concert.id,
                concert.name,
                concert.headlinerImage,
                concert.timeInMillis,
                concert.genre
            )
        )
    }

    override suspend fun removeFavoriteConcert(
        concert: Concert
    ) {
        concertDao.delete(
            RoomConcert(
                concert.id,
                concert.name,
                concert.headlinerImage,
                concert.timeInMillis,
                concert.genre
            )
        )
    }

    override suspend fun getFavoriteConcerts(): List<Concert> {
        return concertDao.getAll().map {
            it.mapAsFavoriteConcert()
        }
    }

    override fun getConcertCached(): List<Concert>? {
        return concertsCached
    }

    override fun updateConcertCached(concerts: List<Concert>?) {
        concertsCached = concerts
    }
}
