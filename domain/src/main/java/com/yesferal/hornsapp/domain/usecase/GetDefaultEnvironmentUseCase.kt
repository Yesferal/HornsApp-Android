package com.yesferal.hornsapp.domain.usecase

import com.yesferal.hornsapp.domain.abstraction.SettingsRepository

class GetDefaultEnvironmentUseCase(
        private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.getDefaultEnvironment()
}