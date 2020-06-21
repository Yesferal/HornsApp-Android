package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.presentation.concert.ConcertsPresenter
import com.yesferal.hornsapp.app.presentation.concert.detail.ConcertPresenter
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.container.resolve
import com.yesferal.hornsapp.hada.dependency.Factory

fun Container.registerPresentationModule() {
    this register Factory<ConcertsPresenter> {
        ConcertsPresenter(
            resolve()
        )
    }

    this register Factory<ConcertPresenter> {
        ConcertPresenter(
            resolve()
        )
    }
}