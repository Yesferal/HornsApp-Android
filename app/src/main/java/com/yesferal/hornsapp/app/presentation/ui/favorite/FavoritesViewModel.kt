package com.yesferal.hornsapp.app.presentation.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.domain.usecase.GetFavoriteConcertsUseCase
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted

class FavoritesViewModel(
    getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase
) : ViewModel() {
    private val _state = MutableLiveData<FavoritesViewState>()

    val state: LiveData<FavoritesViewState>
        get() = _state

    init {
        getFavoriteConcertsUseCase(
            onSuccess = { concerts ->
                _state.value = FavoritesViewState(items = concerts
                    .sortedWith(compareBy { it.dateTime?.time })
                    .map {
                        UpcomingViewData(
                            id = it.id,
                            image = it.headlinerImage,
                            day = it.dateTime?.dayFormatted(),
                            month = it.dateTime?.monthFormatted(),
                            year = it.dateTime?.yearFormatted(),
                            name = it.name,
                            time = it.dateTime?.timeFormatted(),
                            genre = it.genre
                        )
                    }
                )
            },
            onError = {
                _state.value = FavoritesViewState(
                    items = listOf(
                        ErrorViewData(
                            R.drawable.ic_music_note,
                            R.string.error_no_favorite_yet
                        )
                    )
                )
            }
        )
    }
}

class FavoritesViewModelFactory(
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetFavoriteConcertsUseCase::class.java
        ).newInstance(getFavoriteConcertsUseCase)
    }
}