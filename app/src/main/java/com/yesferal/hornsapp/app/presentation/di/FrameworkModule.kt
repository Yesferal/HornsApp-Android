package com.yesferal.hornsapp.app.presentation.di

import androidx.room.Room
import com.google.gson.Gson
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.file.FileReaderDataSource
import com.yesferal.hornsapp.app.framework.logger.YLogger
import com.yesferal.hornsapp.app.framework.preferences.PreferencesDataSource
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitFactory
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitDataSource
import com.yesferal.hornsapp.app.framework.retrofit.ApiConstants
import com.yesferal.hornsapp.app.framework.retrofit.Service
import com.yesferal.hornsapp.app.framework.room.AppDatabase
import com.yesferal.hornsapp.app.framework.room.RoomDataSource
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.data.abstraction.features.DrawerDataSource
import com.yesferal.hornsapp.data.abstraction.features.EnvironmentDataSource
import com.yesferal.hornsapp.data.abstraction.features.FavoriteDataSource
import com.yesferal.hornsapp.data.abstraction.features.OnBoardingDataSource
import com.yesferal.hornsapp.data.abstraction.features.UpdateDrawerDataSource
import com.yesferal.hornsapp.domain.abstraction.Logger
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.dependency.Factory
import com.yesferal.hornsapp.hada.dependency.Singleton
import retrofit2.Retrofit

fun Container.registerFrameworkModule() {

    this register Singleton {
        PreferencesDataSource(
            context = resolve(),
            name = "hornsapp-shared-preferences.sp",
            apiConstants = ApiConstants(),
            gson = resolve()
        )
    }

    this register Factory<OnBoardingDataSource> {
        resolve<PreferencesDataSource>()
    }

    this register Factory<EnvironmentDataSource> {
        resolve<PreferencesDataSource>()
    }

    this register Factory<UpdateDrawerDataSource> {
        resolve<PreferencesDataSource>()
    }

    this register Singleton {
        Gson()
    }

    this register Factory<DrawerDataSource> {
        FileReaderDataSource(name = "app_drawer.json", context = resolve(), gson = resolve())
    }

    this register Singleton {
        val apiConstants = ApiConstants()
        val defaultEnvironment = resolve<PreferencesDataSource>()
                .getDefaultEnvironment()

        RetrofitFactory(
            authorization = apiConstants.authorizations[defaultEnvironment],
            baseUrl = apiConstants.environments[defaultEnvironment].second,
            gson = resolve()
        ).retrofit
    }

    this register Factory<ApiDataSource> {
        val service = resolve<Retrofit>()
            .create(Service::class.java)

        RetrofitDataSource(
            service = service
        )
    }

    this register Singleton {
        AdManager(adUnitIds = AdUnitIds())
    }

    this register Singleton {
        Room.databaseBuilder(
            resolve(),
            AppDatabase::class.java,
            "hornsapp-room-database.db"
        ).build()
    }

    this register Singleton {
        val concertDao = resolve<AppDatabase>().concertDao()

        RoomDataSource(concertDao = concertDao)
    }

    this register Factory<FavoriteDataSource> {
        resolve<RoomDataSource>()
    }

    this register Factory<Logger> {
        YLogger
    }
}
