package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.BandRepository
import com.yesferal.hornsapp.domain.entity.Band

class GetBandUseCase(
    private val bandRepository: BandRepository
) {
    operator fun invoke(
        id: String,
        onSuccess: (response: Band) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {
        bandRepository.getBand(
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