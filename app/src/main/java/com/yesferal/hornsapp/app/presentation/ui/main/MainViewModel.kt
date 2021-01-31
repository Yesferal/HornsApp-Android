package com.yesferal.hornsapp.app.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.framework.adMob.AdViewData

class MainViewModel(
    adManager: AdManager
): ViewModel() {
    private val _adViewData = MutableLiveData<AdViewData>()

    val adViewData: LiveData<AdViewData>
        get() = _adViewData

    init {
        _adViewData.value = adManager.concertsAdView()
    }
}

class MainViewModelFactory(
    private val adManager: AdManager
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
                AdManager::class.java
        ).newInstance(adManager)
    }
}