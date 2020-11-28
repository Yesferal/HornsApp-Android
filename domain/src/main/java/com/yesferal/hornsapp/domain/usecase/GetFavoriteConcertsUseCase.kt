package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.entity.Concert
import java.lang.Exception

class GetFavoriteConcertsUseCase(
    private val concertRepository: ConcertRepository
) {
    operator fun invoke(
        onSuccess: (response: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        val favouriteConcertsIds = concertRepository.getFavoriteConcert()
        val concerts = favouriteConcertsIds?.let { favorites ->
            concertRepository.getConcertsFromStorage()
                ?.filter {
                    favorites.contains(it.id)
                }
        }

        if (concerts == null || concerts.isEmpty()) {
            onError(Exception())
        } else {
            onSuccess(concerts)
        }
    }
}