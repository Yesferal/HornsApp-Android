package com.yesferal.hornsapp.app.presentation.di

import android.content.Context
import com.yesferal.hornsapp.hadi.container.Container
import com.yesferal.hornsapp.hadi.dependency.Singleton

fun Container.registerAppDependencies(context: Context) {
    this register Singleton { context }
    registerDomainModule()
    registerDataModule()
    registerFrameworkModule()
    registerPresentationModule()
}
