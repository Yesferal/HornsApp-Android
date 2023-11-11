/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.core.domain.abstraction.DrawerRepository
import com.yesferal.hornsapp.core.domain.entity.drawer.ViewDrawer
import com.yesferal.hornsapp.core.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val drawerRepository: DrawerRepository
) : ViewModel() {
    private val _state = MutableLiveData<HomeViewState>()
    val state: LiveData<HomeViewState>
        get() = _state

    private lateinit var homeDrawer: List<ViewDrawer>

    init {
        viewModelScope.launch {
            drawerRepository.getHomeDrawer().collect {
                homeDrawer = it
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
                val screens = homeDrawer.map {
                    Pair(it.type, it.data?.title?.text.orEmpty())
                }

                HomeViewState(screens)
            }
            is HaResult.Error -> {
                HomeViewState(errorMessage = R.string.error_default, allowRetry = true)
            }
        }
    }
}

class HomeViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val drawerRepository: DrawerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            DrawerRepository::class.java
        ).newInstance(getConcertsUseCase, drawerRepository)
    }
}
