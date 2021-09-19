package com.yesferal.hornsapp.app.presentation.ui.concert.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.domain.usecase.GetFavoriteConcertsUseCase
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase
) : ViewModel() {

    private val _stateFavorite = MutableLiveData<DelegateViewState>()
    val stateFavorite: LiveData<DelegateViewState>
        get() = _stateFavorite

    fun getFavoriteConcerts() {
        viewModelScope.launch {
            _stateFavorite.value = withContext(Dispatchers.IO) {
                val favorites = getFavoriteConcertsUseCase()

                if (favorites.isEmpty()) {
                    DelegateViewState(
                        delegates = listOf(
                            ErrorViewData(
                                R.drawable.ic_music_note,
                                R.string.error_no_favorite_yet
                            )
                        )
                    )
                } else {
                    val delegates = favorites
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

                    DelegateViewState(delegates)
                }
            }
        }
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
