package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.entity.Concert

class GetConcertsUseCase(
    private val concertRepository: ConcertRepository
) {
    fun invoke(
        onSuccess: (response: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        concertRepository.getConcert(
            onSuccess = {
                onSuccess(it)
            },
            onError = {
                onError(it)
            }
        )
    }
}