package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.presentation.concert.ConcertsPresenter
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertPresenter
import com.yesferal.hornsapp.app.presentation.item.ItemsPresenter
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.container.resolve
import com.yesferal.hornsapp.hada.dependency.Factory

fun Container.registerPresentationModule() {
    this register Factory<ConcertsPresenter> {
        ConcertsPresenter(
            getConcertsUseCase = resolve(),
            getConcertsByCategoryUseCase = resolve(),
            adManager = resolve()
        )
    }

    this register Factory<ConcertPresenter> {
        ConcertPresenter(
            getConcertUseCase = resolve(),
            adManager = resolve(),
            updateFavoriteConcertUseCase = resolve()
        )
    }

    this register Factory<ItemsPresenter> {
        ItemsPresenter(
            getConcertsByCategoryUseCase = resolve()
        )
    }
}