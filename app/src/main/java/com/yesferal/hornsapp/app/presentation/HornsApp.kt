package com.yesferal.hornsapp.app.presentation

import android.app.Application
import android.content.Context
import com.yesferal.hornsapp.app.presentation.di.registerAppDependencies
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.Hada
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.dependency.Singleton

class HornsApp: Application() {
    val container: Container = Hada()

    override fun onCreate() {
        super.onCreate()
        container register Singleton<Context> { this }
        container.registerAppDependencies()
    }
}