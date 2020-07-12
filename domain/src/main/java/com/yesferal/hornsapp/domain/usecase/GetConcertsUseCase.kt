package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.entity.Concert

class GetConcertsUseCase(
    private val concertRepository: ConcertRepository
) {
    operator fun invoke(
        onSuccess: (response: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        val favouriteConcertsIds = concertRepository.getFavoriteConcert()

        concertRepository.getConcerts(
            onSuccess = { concerts ->

                favouriteConcertsIds?.let { favorites ->
                    concerts.forEach { concert ->
                        if (favorites.contains(concert.id)) {
                            concert.isFavorite = true
                        }
                    }
                }

                onSuccess(concerts)
            },
            onError = {
                onError(it)
            }
        )
    }
}