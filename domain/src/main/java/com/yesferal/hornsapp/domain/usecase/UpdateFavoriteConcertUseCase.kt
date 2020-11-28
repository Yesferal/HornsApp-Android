package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository

class UpdateFavoriteConcertUseCase(
    private val concertRepository: ConcertRepository
) {
    operator fun invoke(
        isFavorite: Boolean,
        concertId: String,
        onInsert: () -> Unit,
        onRemove: () -> Unit
    ) {
        if (isFavorite) {
            concertRepository.insertFavoriteConcert(concertId) {
                onInsert()
            }
        } else {
            concertRepository.removeFavoriteConcert(concertId) {
                onRemove()
            }
        }
    }
}