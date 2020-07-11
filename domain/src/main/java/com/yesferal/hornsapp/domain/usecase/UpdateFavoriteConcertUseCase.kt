package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.entity.Concert

class UpdateFavoriteConcertUseCase(
    private val concertRepository: ConcertRepository
) {
    operator fun invoke(
        concert: Concert,
        onInsert: () -> Unit,
        onRemove: () -> Unit
    ) {
        if (concert.isFavorite) {
            concertRepository.insertFavoriteConcert(concert) {
                onInsert()
            }
        } else {
            concertRepository.removeFavoriteConcert(concert) {
                onRemove()
            }
        }
    }
}