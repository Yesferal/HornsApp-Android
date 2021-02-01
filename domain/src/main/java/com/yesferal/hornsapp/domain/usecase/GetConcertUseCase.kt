package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Concert

class GetConcertUseCase(
    private val concertRepository: ConcertRepository
) {
    suspend operator fun invoke(
        id: String
    ): Result<Concert> = concertRepository.getConcert(id)
}