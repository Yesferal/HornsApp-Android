package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import androidx.lifecycle.*
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.base.ViewEffect
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.domain.usecase.GetFavoriteConcertsUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateFavoriteConcertUseCase
import com.yesferal.hornsapp.domain.util.dateTimeFormatted
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ConcertViewModel(
    id: String,
    getConcertUseCase: GetConcertUseCase,
    private val adManager: AdManager,
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
): ViewModel() {
    private val _state = MutableLiveData<ConcertViewState>()
    private val _effect = MutableLiveData<ViewEffect>()

    val state: LiveData<ConcertViewState>
        get() = _state

    val effect: LiveData<ViewEffect>
        get() = _effect

    init {
        // TODO("Change this Use Case as suspend")
        getConcertUseCase(
            id,
            onSuccess = { concert ->
                viewModelScope.launch {
                    _state.value = withContext(Dispatchers.IO) {
                        getFavoriteConcertsUseCase()
                            .map { it.id }
                            .let { favorites ->
                            if (favorites.contains(concert.id)) {
                                concert.isFavorite = true
                            }
                        }

                        val concertViewData = ConcertViewData(
                            concert.id,
                            concert.name,
                            concert.headlinerImage,
                            concert.description,
                            concert.dateTime?.time,
                            concert.dateTime?.dateTimeFormatted(),
                            concert.dateTime?.dayFormatted(),
                            concert.dateTime?.monthFormatted(),
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
                            bands = bandsViewData,
                            adViewData = adManager.concertDetailAdView()
                        )
                    }
                }
            },
            onError = {
                _state.value = ConcertViewState(errorMessageId = R.string.error_default)
            }
        )
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
                    Date().apply { time = concertViewData.timeInMillis?: 0 },
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
    private val adManager: AdManager,
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            GetConcertUseCase::class.java,
            AdManager::class.java,
            GetFavoriteConcertsUseCase::class.java,
            UpdateFavoriteConcertUseCase::class.java
        ).newInstance(id, getConcertUseCase, adManager, getFavoriteConcertsUseCase, updateFavoriteConcertUseCase)
    }
}