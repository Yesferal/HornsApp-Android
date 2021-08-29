package com.yesferal.hornsapp.app.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.framework.adMob.AdViewData
import com.yesferal.hornsapp.domain.abstraction.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    adManager: AdManager,
    settingsRepository: SettingsRepository
) : ViewModel() {
    private val _adViewData = MutableLiveData<AdViewData>()

    val adViewData: LiveData<AdViewData>
        get() = _adViewData

    init {
        _adViewData.value = adManager.concertsAdView()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                settingsRepository.syncAppDrawer()
            }
        }
    }
}

class MainViewModelFactory(
    private val adManager: AdManager,
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            AdManager::class.java,
            SettingsRepository::class.java
        ).newInstance(adManager, settingsRepository)
    }
}
