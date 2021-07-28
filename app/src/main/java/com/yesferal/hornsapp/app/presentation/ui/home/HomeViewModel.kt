package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getConcertsUseCase: GetConcertsUseCase
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
                val titles = listOf("Novedades", "Proximos", "Favoritos")

                HomeViewState(fragmentTitles = titles, concerts = result.value)
            }
            is Result.Error -> {
                HomeViewState(errorMessage = R.string.error_default, allowRetry = true)
            }
        }
    }
}

class HomeViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java
        ).newInstance(getConcertsUseCase)
    }
}
