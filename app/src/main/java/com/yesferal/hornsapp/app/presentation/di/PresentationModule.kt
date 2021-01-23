package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.presentation.ui.band.BandViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.onboarding.OnBoardingViewModelFactory
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.dependency.Factory

fun Container.registerPresentationModule() {
    this register Factory {
        HomeViewModelFactory(
            getConcertsUseCase = resolve(),
            getFavoriteConcertsUseCase = resolve(),
            adManager = resolve()
        )
    }

    this register Factory { (id: String) ->
        ConcertViewModelFactory(
            id = id,
            getConcertUseCase = resolve(),
            getFavoriteConcertsUseCase = resolve(),
            adManager = resolve(),
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
            getConcertsUseCase = resolve()
        )
    }
}