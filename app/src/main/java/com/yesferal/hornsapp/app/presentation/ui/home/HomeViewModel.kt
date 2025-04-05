/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.core.domain.abstraction.RenderRepository
import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import com.yesferal.hornsapp.core.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val drawerRepository: RenderRepository
) : ViewModel() {
    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState>
        get() = _state

    private lateinit var homeDrawer: List<ScreenRender>

    init {
        viewModelScope.launch {
            drawerRepository.getHomeRender().collect { screens ->
                homeDrawer = screens
                onRefresh()
            }
        }
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
        when (getConcertsUseCase()) {
            is HaResult.Success -> {
                HomeViewState(homeDrawer)
            }
            is HaResult.Error -> {
                HomeViewState(errorMessage = R.string.error_default, allowRetry = true)
            }
        }
    }
}

class HomeViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val drawerRepository: RenderRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            RenderRepository::class.java
        ).newInstance(getConcertsUseCase, drawerRepository)
    }
}
