package com.yesferal.hornsapp.data.abstraction

import com.yesferal.hornsapp.domain.entity.Concert

interface StorageDataSource {
    fun insertFavoriteConcert(concertId: String, onComplete: () -> Unit)
    fun removeFavoriteConcert(concertId: String, onComplete: () -> Unit)
    fun getFavoriteConcerts(): MutableList<String>?
    fun insertConcerts(concerts: List<Concert>?)
    fun getConcerts(): List<Concert>?
}