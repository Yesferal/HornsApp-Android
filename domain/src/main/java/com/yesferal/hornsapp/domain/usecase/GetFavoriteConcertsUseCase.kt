package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.entity.Concert

class GetFavoriteConcertsUseCase(
    private val concertRepository: ConcertRepository
) {
    suspend operator fun invoke() : List<Concert> {
        return concertRepository.getFavoriteConcert()
    }
}