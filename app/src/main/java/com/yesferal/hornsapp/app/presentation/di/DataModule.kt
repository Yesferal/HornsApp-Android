/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.core.data.repository.BandRepositoryImpl
import com.yesferal.hornsapp.core.data.repository.ConcertRepositoryImpl
import com.yesferal.hornsapp.core.data.repository.DrawerRepositoryImpl
import com.yesferal.hornsapp.core.data.repository.ServerDrivenRepositoryImpl
import com.yesferal.hornsapp.core.data.repository.SettingsRepositoryImpl
import com.yesferal.hornsapp.core.domain.abstraction.BandRepository
import com.yesferal.hornsapp.core.domain.abstraction.ConcertRepository
import com.yesferal.hornsapp.core.domain.abstraction.DrawerRepository
import com.yesferal.hornsapp.core.domain.abstraction.ServerDrivenRepository
import com.yesferal.hornsapp.core.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.dependency.Singleton

fun Container.registerDataModule() {
    this register Singleton<ConcertRepository> {
        ConcertRepositoryImpl(
            concertRemoteDataSource = resolve(),
            concertStorageDataSource = resolve()
        )
    }

    this register Singleton<BandRepository> {
        BandRepositoryImpl(
            bandRemoteDataSource = resolve()
        )
    }

    this register Singleton<SettingsRepository> {
        SettingsRepositoryImpl(
            environmentDataSource = resolve(),
            onBoardingDataSource = resolve()
        )
    }

    this register Singleton<DrawerRepository> {
        DrawerRepositoryImpl(drawerRemoteDataSource = resolve())
    }

    this register Singleton<ServerDrivenRepository> {
        ServerDrivenRepositoryImpl(reviewRemoteDataSource = resolve())
    }
}
