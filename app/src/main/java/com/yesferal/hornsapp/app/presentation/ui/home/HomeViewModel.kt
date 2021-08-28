package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _state = MutableLiveData<HomeViewState>()

    val state: LiveData<HomeViewState>
        get() = _state

    init {
        onRefresh()
    }

    fun onRefresh() {
        _state.value = HomeViewState(isLoading = true)
        updateViews()
    }

    private fun updateViews() {
        viewModelScope.launch {
            _state.value = getConcerts()
        }
    }

    private suspend fun getConcerts() = withContext(Dispatchers.IO) {
        when (val result = getConcertsUseCase()) {
            is Result.Success -> {
                val screens = settingsRepository.getHomeDrawer().screens?.map {
                    Pair(it.type, it.title?.text.orEmpty())
                }

                HomeViewState(screens, concerts = result.value)
            }
            is Result.Error -> {
                HomeViewState(errorMessage = R.string.error_default, allowRetry = true)
            }
        }
    }
}

class HomeViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            SettingsRepository::class.java
        ).newInstance(getConcertsUseCase, settingsRepository)
    }
}
