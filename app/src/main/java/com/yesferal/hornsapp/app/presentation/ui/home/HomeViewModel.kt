package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase

class HomeViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val adManager: AdManager
): ViewModel() {
    private val _state = MutableLiveData<HomeViewState>()

    val state: LiveData<HomeViewState>
        get() = _state

    init {
        getConcerts()
    }

    fun onRefresh() {
        _state.value = HomeViewState(isLoading = true)
        getConcerts()
    }

    private fun getConcerts() {
        getConcertsUseCase(
            onSuccess = {
                val titles = listOf("Novedades", "Proximos", "Favoritos")

                _state.value = HomeViewState(
                    fragmentTitles = titles,
                    adViewData = adManager.concertsAdView())
            },
            onError = {
                _state.value = HomeViewState(
                    errorMessage = R.string.error_default,
                    allowRetry = true
                )
            }
        )
    }
}

class HomeViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val adManager: AdManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            AdManager::class.java
        ).newInstance(getConcertsUseCase, adManager)
    }
}