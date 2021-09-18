package com.yesferal.hornsapp.data.abstraction.storage

import com.yesferal.hornsapp.domain.entity.Concert

interface ConcertStorageDataSource {
    suspend fun insertFavoriteConcert(concert: Concert)
    suspend fun removeFavoriteConcert(concert: Concert)
    suspend fun getFavoriteConcerts(): List<Concert>
}
