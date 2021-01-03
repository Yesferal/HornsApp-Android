package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.presentation.ui.band.BandViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.favorite.FavoritesViewModelFactory
import com.yesferal.hornsapp.app.presentation.ui.home.HomeViewModelFactory
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.dependency.Factory

fun Container.registerPresentationModule() {
    this register Factory {
        FavoritesViewModelFactory(
            getFavoriteConcertsUseCase = resolve()
        )
    }

    this register Factory {
        HomeViewModelFactory(
            getConcertsUseCase = resolve(),
            adManager = resolve()
        )
    }

    this register Factory {
        UpcomingViewModelFactory(
            getConcertsByCategoryUseCase = resolve()
        )
    }

    this register Factory {
        NewestViewModelFactory(
            getConcertsByCategoryUseCase = resolve()
        )
    }

    this register Factory { (id: String) ->
        ConcertViewModelFactory(
            id = id,
            getConcertUseCase = resolve(),
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
}