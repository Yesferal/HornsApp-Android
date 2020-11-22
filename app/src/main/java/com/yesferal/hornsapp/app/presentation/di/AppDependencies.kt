package com.yesferal.hornsapp.app.presentation.di

import android.content.Context
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.dependency.Singleton

fun Container.registerAppDependencies(context: Context) {
    this register Singleton<Context> { context }
    registerDomainModule()
    registerDataModule()
    registerFrameworkModule()
    registerPresentationModule()
}