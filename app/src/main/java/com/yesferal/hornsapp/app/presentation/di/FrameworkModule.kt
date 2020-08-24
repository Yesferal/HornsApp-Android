package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitBuilder
import com.yesferal.hornsapp.app.framework.retrofit.RetrofitDataSource
import com.yesferal.hornsapp.app.framework.preferences.SharedPreferencesDataSource
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.data.abstraction.StorageDataSource
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.container.resolve
import com.yesferal.hornsapp.hada.dependency.Factory
import com.yesferal.hornsapp.hada.dependency.Singleton

fun Container.registerFrameworkModule() {
    this register Singleton<StorageDataSource> {
        SharedPreferencesDataSource(
            context = resolve()
        )
    }

    this register Singleton<ApiDataSource> {
        val service = RetrofitBuilder()
            .createService()
        RetrofitDataSource(service)
    }

    this register Factory<AdManager> {
        AdManager(
            adUnitIds = AdUnitIds(),
            context = resolve()
        )
    }
}