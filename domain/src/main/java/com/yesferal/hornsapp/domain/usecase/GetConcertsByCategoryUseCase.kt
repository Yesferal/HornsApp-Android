package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.entity.Concert
import java.lang.Exception

class GetConcertsByCategoryUseCase(
    private val concertRepository: ConcertRepository
) {
    operator fun invoke(
        categoryKey: String,
        onSuccess: (response: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        val concerts = when (categoryKey) {
            CategoryKey.LIVE.toString(),
            CategoryKey.ONLINE.toString() -> {
                concertRepository.getConcertsFromStorage()
                    ?.filter { it.category == categoryKey }
            }
            CategoryKey.FAVORITE.toString() -> {
                val favouriteConcertsIds = concertRepository.getFavoriteConcert()
                favouriteConcertsIds?.let { favorites ->
                    concertRepository.getConcertsFromStorage()
                        ?.filter {
                            favorites.contains(it.id)
                        }
                }
            }
            else -> { null }
        }

        if (concerts == null || concerts.isEmpty()) {
            onError(Exception())
        } else {
            onSuccess(concerts)
        }
    }
}