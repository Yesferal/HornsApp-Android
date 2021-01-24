package com.yesferal.hornsapp.app.presentation.ui.band

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.domain.usecase.GetBandUseCase

class BandViewModel(
    id: String,
    getBandUseCase: GetBandUseCase
) : ViewModel() {
    private val _state = MutableLiveData<BandViewState>()

    val state: LiveData<BandViewState>
        get() = _state

    init {
        getBandUseCase(
            id,
            onSuccess = {
                _state.value = BandViewState(band = it)
            },
            onError = {
                _state.value = BandViewState(errorMessageId = R.string.error_default)
            }
        )
    }
}

class BandViewModelFactory(
    private val id: String,
    private val getBandUseCase: GetBandUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            GetBandUseCase::class.java
        ).newInstance(id, getBandUseCase)
    }
}