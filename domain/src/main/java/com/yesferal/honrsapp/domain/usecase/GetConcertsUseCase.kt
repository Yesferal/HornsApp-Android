package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.repository.ConcertRepository

class GetConcertsUseCase(
    private val concertRepository: ConcertRepository
) : BaseUseCase<Nothing, List<Concert>> {
    override fun invoke(
        param: Nothing?,
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