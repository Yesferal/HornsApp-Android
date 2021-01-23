package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.entity.Concert

class UpdateFavoriteConcertUseCase(
    private val concertRepository: ConcertRepository
) {
    suspend operator fun invoke(
        concert: Concert
    ) {
        return if (concert.isFavorite) {
            concertRepository.insertFavoriteConcert(concert)
        } else {
            concertRepository.removeFavoriteConcert(concert)
        }
    }
}