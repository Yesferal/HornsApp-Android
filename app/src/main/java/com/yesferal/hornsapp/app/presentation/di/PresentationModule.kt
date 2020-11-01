package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.presentation.ui.band.BandPresenter
import com.yesferal.hornsapp.app.presentation.ui.concert.search.ConcertsPresenter
import com.yesferal.hornsapp.app.presentation.ui.concert.detail.ConcertPresenter
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingPresenter
import com.yesferal.hornsapp.app.presentation.ui.favorite.FavoritesPresenter
import com.yesferal.hornsapp.app.presentation.ui.home.HomePresenter
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.container.resolve
import com.yesferal.hornsapp.hada.dependency.Factory

fun Container.registerPresentationModule() {
    this register Factory<HomePresenter> {
        HomePresenter(
            getConcertsUseCase = resolve(),
            adManager = resolve()
        )
    }

    this register Factory<ConcertsPresenter> {
        ConcertsPresenter(
            getConcertsByCategoryUseCase = resolve()
        )
    }

    this register Factory<UpcomingPresenter> {
        UpcomingPresenter(
            getConcertsByCategoryUseCase = resolve()
        )
    }

    this register Factory<FavoritesPresenter> {
        FavoritesPresenter(
            getConcertsByCategoryUseCase = resolve()
        )
    }

    this register Factory<ConcertPresenter> {
        ConcertPresenter(
            getConcertUseCase = resolve(),
            adManager = resolve(),
            updateFavoriteConcertUseCase = resolve()
        )
    }

    this register Factory<BandPresenter> {
        BandPresenter(
            getBandUseCase = resolve()
        )
    }
}