package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitBuilder
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitDataSource
import com.yesferal.hornsapp.app.framework.preferences.SharedPreferencesDataSource
import com.yesferal.hornsapp.app.framework.retrofit.ApiConstants
import com.yesferal.hornsapp.app.framework.retrofit.Service
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.data.abstraction.StorageDataSource
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.dependency.Singleton

fun Container.registerFrameworkModule() {
    this register Singleton<StorageDataSource> {
        SharedPreferencesDataSource(
            context = resolve()
        )
    }

    this register Singleton<ApiDataSource> {
        val service = RetrofitBuilder(ApiConstants())
            .retrofit
            .create(Service::class.java)

        RetrofitDataSource(service = service)
    }

    this register Singleton {
        AdManager(
            adUnitIds = AdUnitIds()
        )
    }
}