package com.yesferal.hornsapp.data.abstraction.features

import com.yesferal.hornsapp.domain.entity.Concert

interface FavoriteDataSource {
    suspend fun insertFavoriteConcert(concert: Concert)
    suspend fun removeFavoriteConcert(concert: Concert)
    suspend fun getFavoriteConcerts(): List<Concert>
}