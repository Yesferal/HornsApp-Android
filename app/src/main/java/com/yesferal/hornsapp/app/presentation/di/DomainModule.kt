package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.domain.usecase.*
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.container.resolve
import com.yesferal.hornsapp.hada.dependency.Factory

fun Container.registerDomainModule() {
    this register Factory<GetConcertsUseCase> {
        GetConcertsUseCase(
            concertRepository = resolve()
        )
    }

    this register Factory<GetConcertUseCase> {
        GetConcertUseCase(
            concertRepository = resolve()
        )
    }

    this register Factory<GetConcertsByCategoryUseCase> {
        GetConcertsByCategoryUseCase(
            concertRepository = resolve()
        )
    }

    this register Factory<UpdateFavoriteConcertUseCase> {
        UpdateFavoriteConcertUseCase(
            concertRepository = resolve()
        )
    }

    this register Factory<GetBandUseCase> {
        GetBandUseCase(
            bandRepository = resolve()
        )
    }
}