/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.core.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LineupViewModel(
    id: String,
    getConcertUseCase: GetConcertUseCase,
) : ViewModel() {
    private val _state = MutableLiveData<LineupViewState>()

    val state: LiveData<LineupViewState>
        get() = _state

    init {
        viewModelScope.launch {
            val state = withContext(Dispatchers.IO) {
                when (val result = getConcertUseCase(id)) {
                    is HaResult.Success -> {
                        val lineup = result.value.lineup
                        LineupViewState(
                            headers = lineup?.firstOrNull()?.stages?.mapNotNull { it.title },
                            stages = lineup?.firstOrNull()?.stages
                        )
                    }
                    is HaResult.Error -> {
                        LineupViewState(errorMessageId = R.string.error_default)
                    }
                }
            }
            _state.value = state
        }
    }
}

class LineupViewModelFactory(
    private val id: String,
    private val getConcertUseCase: GetConcertUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            GetConcertUseCase::class.java,
        ).newInstance(
            id,
            getConcertUseCase,
        )
    }
}
