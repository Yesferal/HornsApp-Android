package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.hada.container.Container

fun Container.registerAppDependencies() {
    registerDomainModule()
    registerDataModule()
    registerFrameworkModule()
    registerPresentationModule()
}