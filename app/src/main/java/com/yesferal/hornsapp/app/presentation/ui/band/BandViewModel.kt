package com.yesferal.hornsapp.app.presentation.ui.band

import androidx.lifecycle.*
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.usecase.GetBandUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BandViewModel(
    id: String,
    getBandUseCase: GetBandUseCase
) : ViewModel() {
    private val _state = MutableLiveData<BandViewState>()

    val state: LiveData<BandViewState>
        get() = _state

    init {
        viewModelScope.launch {
            _state.value = withContext(Dispatchers.IO) {
                when (val result = getBandUseCase(id)) {
                    is Result.Success -> {
                        BandViewState(band = result.value)
                    }
                    is Result.Error -> {
                        BandViewState(errorMessageId = R.string.error_default)
                    }
                }
            }
        }
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