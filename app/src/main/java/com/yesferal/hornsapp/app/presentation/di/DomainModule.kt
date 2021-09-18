package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.domain.usecase.GetBandUseCase
import com.yesferal.hornsapp.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.usecase.GetDefaultEnvironmentUseCase
import com.yesferal.hornsapp.domain.usecase.GetFavoriteConcertsUseCase
import com.yesferal.hornsapp.domain.usecase.GetSettingsUseCase
import com.yesferal.hornsapp.domain.usecase.GetVisibilityOnBoardingUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateFavoriteConcertUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateSettingsUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateVisibilityOnBoardingUseCase
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.dependency.Factory

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
