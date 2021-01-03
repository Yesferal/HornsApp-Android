package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.base.ViewEffect
import com.yesferal.hornsapp.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateFavoriteConcertUseCase
import com.yesferal.hornsapp.domain.util.dateTimeFormatted
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted

class ConcertViewModel(
    id: String,
    getConcertUseCase: GetConcertUseCase,
    private val adManager: AdManager,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
): ViewModel() {
    private val _state = MutableLiveData<ConcertViewState>()
    private val _effect = MutableLiveData<ViewEffect>()

    val state: LiveData<ConcertViewState>
        get() = _state

    val effect: LiveData<ViewEffect>
        get() = _effect

    init {
        getConcertUseCase(
            id,
            onSuccess = {
                val concert = ConcertViewData(
                    it.id,
                    it.name,
                    it.description,
                    it.dateTime?.time,
                    it.dateTime?.dateTimeFormatted(),
                    it.dateTime?.dayFormatted(),
                    it.dateTime?.monthFormatted(),
                    it.trailerUrl,
                    it.facebookUrl,
                    it.isFavorite,
                    it.genre,
                    it.ticketingHost,
                    it.ticketingUrl,
                    it.venue
                )

                val bands = it.bands?.map { band ->
                    BandViewData(
                        band.id,
                        band.name,
                        band.membersImage,
                        band.genre
                    )
                }

                _state.value = ConcertViewState(
                    concert = concert,
                    bands = bands,
                    adViewData = adManager.concertDetailAdView()
                )
            },
            onError = {
                _state.value = ConcertViewState(errorMessageId = R.string.error_default)
            }
        )
    }

    fun onFavoriteImageViewClick(
        concert: ConcertViewData,
        isChecked: Boolean
    ) {
        concert.isFavorite = isChecked
        updateFavoriteConcertUseCase(
            concert.isFavorite,
            concert.id,
            onInsert = {
                _effect.value = ViewEffect.Toast(R.string.add_to_favorite)
            },
            onRemove = {}
        )
    }
}

class ConcertViewModelFactory(
    private val id: String,
    private val getConcertUseCase: GetConcertUseCase,
    private val adManager: AdManager,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            String::class.java,
            GetConcertUseCase::class.java,
            AdManager::class.java,
            UpdateFavoriteConcertUseCase::class.java
        ).newInstance(id, getConcertUseCase, adManager, updateFavoriteConcertUseCase)
    }
}