package com.yesferal.hornsapp.app.presentation.di

import androidx.room.Room
import com.google.gson.Gson
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.file.FileReaderManager
import com.yesferal.hornsapp.app.framework.logger.YLogger
import com.yesferal.hornsapp.app.framework.preferences.PreferencesDataSource
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitFactory
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitDataSource
import com.yesferal.hornsapp.app.framework.retrofit.ApiConstants
import com.yesferal.hornsapp.app.framework.retrofit.Service
import com.yesferal.hornsapp.app.framework.room.AppDatabase
import com.yesferal.hornsapp.app.framework.room.RoomDataSource
import com.yesferal.hornsapp.app.framework.socketio.SocketIoDataSource
import com.yesferal.hornsapp.data.abstraction.storage.DrawerStorageDataSource
import com.yesferal.hornsapp.data.abstraction.storage.EnvironmentDataSource
import com.yesferal.hornsapp.data.abstraction.storage.ConcertStorageDataSource
import com.yesferal.hornsapp.data.abstraction.storage.OnBoardingDataSource
import com.yesferal.hornsapp.data.abstraction.remote.BandRemoteDataSource
import com.yesferal.hornsapp.data.abstraction.remote.ConcertRemoteDataSource
import com.yesferal.hornsapp.data.abstraction.remote.DrawerRemoteDataSource
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
            gson = resolve(),
            fileReaderManager = resolve()
        )
    }

    this register Factory<OnBoardingDataSource> {
        resolve<PreferencesDataSource>()
    }

    this register Factory<EnvironmentDataSource> {
        resolve<PreferencesDataSource>()
    }

    this register Factory<DrawerStorageDataSource> {
        resolve<PreferencesDataSource>()
    }

    this register Singleton { Gson() }

    this register Factory {
        FileReaderManager(context = resolve())
    }

    this register Singleton {
        val defaultEnvironment = resolve<PreferencesDataSource>()
            .getDefaultEnvironment()
        val apiConstants = ApiConstants()

        RetrofitFactory(
            authorization = apiConstants.authorizations[defaultEnvironment],
            baseUrl = apiConstants.environments[defaultEnvironment].second,
            gson = resolve()
        ).retrofit
    }

    this register Singleton {
        val service = resolve<Retrofit>()
            .create(Service::class.java)

        RetrofitDataSource(service = service)
    }

    this register Factory<ConcertRemoteDataSource> {
        resolve<RetrofitDataSource>()
    }

    this register Factory<BandRemoteDataSource> {
        resolve<RetrofitDataSource>()
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

    this register Factory<ConcertStorageDataSource> {
        resolve<RoomDataSource>()
    }

    this register Singleton {
        val defaultEnvironment = resolve<PreferencesDataSource>()
            .getDefaultEnvironment()
        val apiConstants = ApiConstants()

        SocketIoDataSource(
            gson = resolve(),
            logger = resolve(),
            baseUrl = apiConstants.environments[defaultEnvironment].second,
            defaultAppDrawer = resolve<DrawerStorageDataSource>().getAppDrawer()
        )
    }

    this register Factory<DrawerRemoteDataSource> {
        resolve<SocketIoDataSource>()
    }

    this register Factory<Logger> {
        YLogger
    }

    this register Singleton {
        AdManager(adUnitIds = AdUnitIds())
    }
}
