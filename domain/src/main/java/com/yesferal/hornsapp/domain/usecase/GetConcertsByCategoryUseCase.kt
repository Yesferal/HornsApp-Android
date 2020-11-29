package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.entity.Concert
import java.lang.Exception

class GetConcertsByCategoryUseCase(
    private val concertRepository: ConcertRepository
) {
    operator fun invoke(
        categoryKey: CategoryKey,
        onSuccess: (response: List<Concert>) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        val concerts = when (categoryKey) {
            CategoryKey.ALL -> {
                concertRepository.getConcertsFromStorage()
            }
            else -> {
                concertRepository.getConcertsFromStorage()
                    ?.filter { it.tags?.contains(categoryKey.toString()) == true }
            }
        }

        if (concerts == null || concerts.isEmpty()) {
            onError(Exception())
        } else {
            onSuccess(concerts)
        }
    }
}