package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.render.ViewEffect
import com.yesferal.hornsapp.core.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.core.domain.usecase.UpdateFavoriteConcertUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConcertViewModel(
    id: String,
    getConcertUseCase: GetConcertUseCase,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
) : ViewModel() {
    private val _state = MutableLiveData<ConcertViewState>()
    private val _effect = MutableLiveData<ViewEffect>()

    val state: LiveData<ConcertViewState>
        get() = _state

    val effect: LiveData<ViewEffect>
        get() = _effect

    init {
        viewModelScope.launch {
            val state = withContext(Dispatchers.IO) {
                when (val result = getConcertUseCase(id)) {
                    is HaResult.Success -> {
                        val concert = result.value

                        val concertViewData = ConcertViewData(concert)

                        var count = 0
                        val bandsViewData = mutableListOf<BandViewData>()

                        if (concert.bands.isNullOrEmpty()) {
                            // TODO: Use String En & Es to handle TBA, not hardcoded
                            bandsViewData.add(BandViewData(null, "TBA", concert.headlinerImage, 0, 0))
                        } else {
                            concert.bands?.reversed()?.map { band ->
                                count++
                                bandsViewData.add(BandViewData(
                                    band.id,
                                    band.name,
                                    band.membersImage,
                                    count,
                                    concert.bands?.size
                                ))
                            }
                            // TODO: Use String En & Es to handle Headliners, not hardcoded
                            bandsViewData.add(0, BandViewData(null, "Headliners", concert.headlinerImage, 0, concert.bands?.size))
                        }

                        ConcertViewState(
                            concert = concertViewData,
                            bands = bandsViewData
                        )
                    }
                    is HaResult.Error -> {
                        ConcertViewState(errorMessageId = R.string.error_default)
                    }
                }
            }
            _state.value = state
        }
    }

    fun onFavoriteImageViewClick(
        concertViewData: ConcertViewData,
        isChecked: Boolean
    ) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                updateFavoriteConcertUseCase(concertViewData.concert, isChecked)
            }

            if (isChecked) {
                _effect.value = ViewEffect.Toast(R.string.add_to_favorite)
            }
        }
    }
}

class ConcertViewModelFactory(
    private val id: String,
    private val getConcertUseCase: GetConcertUseCase,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            GetConcertUseCase::class.java,
            UpdateFavoriteConcertUseCase::class.java
        ).newInstance(
            id,
            getConcertUseCase,
            updateFavoriteConcertUseCase
        )
    }
}
