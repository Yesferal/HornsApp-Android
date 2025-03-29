/* Copyright © 2025 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.lineup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.framework.logger.ChainLoggerProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LineupViewModel(
    id: String,
    private val lineupDataSource: LineupDataSource,
) : ViewModel() {
    private val _state = MutableLiveData<LineupViewState>()

    val state: LiveData<LineupViewState>
        get() = _state

    init {
        viewModelScope.launch {
            val state = withContext(Dispatchers.IO) {
                val result = lineupDataSource.getLineup()
                val lineup = result
                ChainLoggerProvider.provideLogger().d("LineupViewModel: lineup: ${lineup}")
                LineupViewState(
                    day = lineup?.day,
                    headers = lineup?.stages?.mapNotNull { it.title },
                    stages = lineup?.stages
                )
            }
            _state.value = state
        }
    }
}

class LineupViewModelFactory(
    private val id: String,
    private val lineupDataSource: LineupDataSource,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            LineupDataSource::class.java,
        ).newInstance(
            id,
            lineupDataSource,
        )
    }
}
