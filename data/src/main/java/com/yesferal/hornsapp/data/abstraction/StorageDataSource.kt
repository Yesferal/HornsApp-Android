package com.yesferal.hornsapp.data.abstraction

import com.yesferal.hornsapp.domain.entity.Concert

interface StorageDataSource {
    fun insertFavoriteConcert(concert: Concert, onComplete: () -> Unit)
    fun removeFavoriteConcert(concert: Concert, onComplete: () -> Unit)
    fun getFavoriteConcerts(): MutableList<String>?
}