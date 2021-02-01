package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.BandRepository

class GetBandUseCase(
    private val bandRepository: BandRepository
) {
    suspend operator fun invoke(
        id: String
    ) = bandRepository.getBand(id)
}