/* Copyright © 2021 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.framework.adMob.AbstractViewFactory
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer

class MainViewModel(
    businessModelFactoryProducer: BusinessModelFactoryProducer
) : ViewModel() {

    private val _viewFactory = MutableLiveData<AbstractViewFactory>()
    val viewFactory: LiveData<AbstractViewFactory>
        get() = _viewFactory

    init {
        _viewFactory.value = businessModelFactoryProducer.getViewFactory()
    }
}

class MainViewModelFactory(
    private val businessModelFactoryProducer: BusinessModelFactoryProducer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            BusinessModelFactoryProducer::class.java
        ).newInstance(businessModelFactoryProducer)
    }
}
