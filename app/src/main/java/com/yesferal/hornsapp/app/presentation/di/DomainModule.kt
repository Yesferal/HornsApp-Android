package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.core.domain.usecase.GetBandUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetDefaultEnvironmentUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetFavoriteConcertsUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetSettingsUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetVisibilityOnBoardingUseCase
import com.yesferal.hornsapp.core.domain.usecase.UpdateFavoriteConcertUseCase
import com.yesferal.hornsapp.core.domain.usecase.UpdateSettingsUseCase
import com.yesferal.hornsapp.core.domain.usecase.UpdateVisibilityOnBoardingUseCase
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.dependency.Factory

fun Container.registerDomainModule() {
    this register Factory {
        GetConcertsUseCase(
            concertRepository = resolve()
        )
    }

    this register Factory {
        GetConcertUseCase(
            concertRepository = resolve()
        )
    }

    this register Factory {
        GetFavoriteConcertsUseCase(
            concertRepository = resolve()
        )
    }

    this register Factory {
        UpdateFavoriteConcertUseCase(
            concertRepository = resolve()
        )
    }

    this register Factory {
        GetBandUseCase(
            bandRepository = resolve()
        )
    }

    this register Factory {
        GetSettingsUseCase(
                settingsRepository = resolve()
        )
    }

    this register Factory {
        GetDefaultEnvironmentUseCase(
                settingsRepository = resolve()
        )
    }

    this register Factory {
        UpdateSettingsUseCase(
                settingsRepository = resolve()
        )
    }

    this register Factory {
        GetVisibilityOnBoardingUseCase(
            settingsRepository = resolve()
        )
    }

    this register Factory {
        UpdateVisibilityOnBoardingUseCase(
            settingsRepository = resolve()
        )
    }
}
