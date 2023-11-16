/* Copyright © 2021 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.presentation.ui.band.BandViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.concert.favorite.FavoritesViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.main.MainViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.onboarding.OnBoardingViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.settings.SettingsViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.splash.SplashViewModelFactory
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.dependency.Factory

fun Container.registerPresentationModule() {
    this register Factory {
        MainViewModelFactory(
            businessModelFactoryProducer = resolve()
        )
    }

    this register Factory {
        HomeViewModelFactory(
            getConcertsUseCase = resolve(),
            drawerRepository = resolve()
        )
    }

    this register Factory {
        FavoritesViewModelFactory(
            getFavoriteConcertsUseCase = resolve(),
            settingsRepository = resolve(),
            businessModelFactoryProducer = resolve()
        )
    }

    this register Factory {
        UpcomingViewModelFactory(
            getUpcomingConcertsUseCase = resolve(),
            settingsRepository = resolve(),
            drawerRepository = resolve(),
            businessModelFactoryProducer = resolve()
        )
    }

    this register Factory {
        NewestViewModelFactory(
            businessModelFactoryProducer = resolve(),
            getConcertsUseCase = resolve(),
            drawerRepository = resolve(),
            logger = resolve()
        )
    }

    this register Factory { (id: String) ->
        ConcertViewModelFactory(
            id = id,
            getConcertUseCase = resolve(),
            updateFavoriteConcertUseCase = resolve()
        )
    }

    this register Factory { (id: String) ->
        BandViewModelFactory(
            id = id,
            getBandUseCase = resolve()
        )
    }

    this register Factory {
        OnBoardingViewModelFactory(
            getConcertsUseCase = resolve(),
            updateVisibilityOnBoardingUseCase = resolve(),
            drawerRepository = resolve(),
            filterConcertsByCategoryUseCase = resolve()
        )
    }

    this register Factory {
        SettingsViewModelFactory(
            getDefaultEnvironmentUseCase = resolve(),
            getSettingsUseCase = resolve(),
            updateSettingsUseCase = resolve()
        )
    }

    this register Factory {
        SplashViewModelFactory(
            getVisibilityOnBoardingUseCase = resolve()
        )
    }
}
