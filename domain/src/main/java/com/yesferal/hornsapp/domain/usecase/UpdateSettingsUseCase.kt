package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.SettingsRepository

class UpdateSettingsUseCase(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(environment: Int) {
        settingsRepository.updateDefaultEnvironment(environment)
    }
}