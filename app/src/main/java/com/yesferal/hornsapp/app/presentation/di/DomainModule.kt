package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateFavoriteConcertUseCase
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

    this register Factory<UpdateFavoriteConcertUseCase> {
        UpdateFavoriteConcertUseCase(
            concertRepository = resolve()
        )
    }
}