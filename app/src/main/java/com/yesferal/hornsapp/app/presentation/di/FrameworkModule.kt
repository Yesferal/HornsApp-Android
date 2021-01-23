package com.yesferal.hornsapp.app.presentation.di

import androidx.room.Room
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.preferences.SharedPreferencesDataSource
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitFactory
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitDataSource
import com.yesferal.hornsapp.app.framework.retrofit.ApiConstants
import com.yesferal.hornsapp.app.framework.retrofit.Service
import com.yesferal.hornsapp.app.framework.room.AppDatabase
import com.yesferal.hornsapp.app.framework.room.RoomDataSource
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.data.abstraction.OrmDataSource
import com.yesferal.hornsapp.data.abstraction.PreferencesDataSource
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.dependency.Factory
import com.yesferal.hornsapp.hada.dependency.Singleton
import retrofit2.Retrofit

fun Container.registerFrameworkModule() {
    this register Singleton<PreferencesDataSource> {
        SharedPreferencesDataSource(
            context = resolve(),
            name = "hornsapp-shared-preferences.sp",
            apiConstants = ApiConstants()
        )
    }

    this register Singleton {
        RetrofitFactory(
            constants = ApiConstants(),
            environment = resolve<PreferencesDataSource>().getEnvironment()
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
        AdManager(
            adUnitIds = AdUnitIds()
        )
    }

    this register Singleton {
        Room.databaseBuilder(
            resolve(),
            AppDatabase::class.java,
            "hornsapp-room-database.db"
        ).build()
    }

    this register Factory<OrmDataSource> {
        val concertDao = resolve<AppDatabase>().concertDao()

        RoomDataSource(
            concertDao = concertDao
        )
    }
}