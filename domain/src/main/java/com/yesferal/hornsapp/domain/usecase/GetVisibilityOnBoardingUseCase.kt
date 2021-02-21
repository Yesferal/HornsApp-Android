package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.SettingsRepository

class GetVisibilityOnBoardingUseCase (
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.onBoardingIsVisible()
}