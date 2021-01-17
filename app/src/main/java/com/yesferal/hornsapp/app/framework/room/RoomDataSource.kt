package com.yesferal.hornsapp.app.framework.room

import com.yesferal.hornsapp.app.framework.room.entity.RoomConcert
import com.yesferal.hornsapp.data.abstraction.OrmDataSource
import com.yesferal.hornsapp.domain.entity.Concert

class RoomDataSource(
    private val concertDao: ConcertDao
): OrmDataSource {
    override suspend fun insertFavoriteConcert(
        concert: Concert
    ) {
        concertDao.insert(
            RoomConcert(
                concert.id,
                concert.name,
                concert.headlinerImage,
                concert.dateTime?.time,
                concert.genre
            )
        )
    }

    override suspend fun removeFavoriteConcert(
        concert: Concert
    ) {
        concertDao.delete(RoomConcert(
            concert.id,
            concert.name,
            concert.headlinerImage,
            concert.dateTime?.time,
            concert.genre
        ))
    }

    override suspend fun getFavoriteConcerts(): List<Concert> {
        return concertDao.getAll().map {
            it.mapTo()
        }
    }
}