package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.framework.datasource.api.RetrofitBuilder
import com.yesferal.hornsapp.app.framework.datasource.api.RetrofitDataSource
import com.yesferal.hornsapp.app.framework.datasource.storage.SharedPreferencesDataSource
import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.data.abstraction.StorageDataSource
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.container.resolve
import com.yesferal.hornsapp.hada.dependency.Singleton

fun Container.registerFrameworkModule() {
    this register Singleton<StorageDataSource> {
        SharedPreferencesDataSource(
            resolve()
        )
    }

    this register Singleton<ApiDataSource> {
        val service = RetrofitBuilder()
            .createService()
        RetrofitDataSource(service)
    }
}