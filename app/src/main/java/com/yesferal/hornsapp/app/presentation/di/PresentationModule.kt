package com.yesferal.hornsapp.app.presentation.di

import com.yesferal.hornsapp.app.presentation.concerts.ConcertsPresenter
import com.yesferal.hornsapp.hada.container.Container
import com.yesferal.hornsapp.hada.container.register
import com.yesferal.hornsapp.hada.container.resolve
import com.yesferal.hornsapp.hada.dependency.Factory

fun Container.registerPresentationModule() {
    this register Factory<ConcertsPresenter> {
        ConcertsPresenter(resolve())
    }
}