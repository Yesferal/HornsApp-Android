package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.render.ViewEffect
import com.yesferal.hornsapp.core.domain.common.HaDate
import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetFavoriteConcertsUseCase
import com.yesferal.hornsapp.core.domain.usecase.UpdateFavoriteConcertUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConcertViewModel(
    id: String,
    getConcertUseCase: GetConcertUseCase,
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase,
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
                        getFavoriteConcertsUseCase()
                            .map { it.id }
                            .let { favorites ->
                                if (favorites.contains(concert.id)) {
                                    concert.isFavorite = true
                                }
                            }

                        val haDate = HaDate(concert.dateTime)

                        val concertViewData = ConcertViewData(
                            concert.id,
                            concert.name,
                            concert.headlinerImage,
                            concert.description,
                            haDate.time,
                            haDate.dateTimeFormatted(),
                            haDate.dayFormatted(),
                            haDate.monthFormatted(),
                            concert.trailerUrl,
                            concert.facebookUrl,
                            concert.isFavorite,
                            concert.genre,
                            concert.ticketingHost,
                            concert.ticketingUrl,
                            concert.venue
                        )

                        val bandsViewData = concert.bands?.map { band ->
                            BandViewData(
                                band.id,
                                band.name,
                                band.membersImage,
                                band.genre
                            )
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
                val concert = Concert(
                    concertViewData.id,
                    concertViewData.name,
                    concertViewData.headlinerImage,
                    concertViewData.timeInMillis,
                    concertViewData.genre,
                    null,
                    isChecked
                )

                updateFavoriteConcertUseCase(concert)
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
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            GetConcertUseCase::class.java,
            GetFavoriteConcertsUseCase::class.java,
            UpdateFavoriteConcertUseCase::class.java
        ).newInstance(
            id,
            getConcertUseCase,
            getFavoriteConcertsUseCase,
            updateFavoriteConcertUseCase
        )
    }
}
