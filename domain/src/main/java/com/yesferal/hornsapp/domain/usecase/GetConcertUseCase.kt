package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.entity.Concert

class GetConcertUseCase(
    private val concertRepository: ConcertRepository
) {
    operator fun invoke(
        id: String,
        onSuccess: (response: Concert) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        concertRepository.getConcert(
            id,
            onSuccess = {
                onSuccess(it)
            },
            onError = {
                onError(it)
            }
        )
    }
}