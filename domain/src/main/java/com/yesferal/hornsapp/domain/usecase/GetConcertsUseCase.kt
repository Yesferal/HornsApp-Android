package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository

class GetConcertsUseCase(
    private val concertRepository: ConcertRepository
) {
    suspend operator fun invoke() = concertRepository.getConcerts()
}