package com.yesferal.hornsapp.app.presentation.ui.concert.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdUnitIds
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.common.extension.dayFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.monthFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.safeInsert
import com.yesferal.hornsapp.app.presentation.common.extension.timeFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.yearFormatted
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.AdViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.core.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.core.domain.usecase.GetFavoriteConcertsUseCase
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _stateFavorite = MutableLiveData<DelegateViewState>()
    val stateFavorite: LiveData<DelegateViewState>
        get() = _stateFavorite

    fun getFavoriteConcerts() {
        viewModelScope.launch {
            delay(settingsRepository.screenDelay)
            val stateFavorite = withContext(Dispatchers.IO) {
                val favorites = getFavoriteConcertsUseCase()

                if (favorites.isEmpty()) {
                    DelegateViewState(
                        delegates = listOf(
                            ErrorViewData(
                                R.drawable.ic_favorite,
                                R.string.error_no_favorite_yet
                            )
                        )
                    )
                } else {
                    val delegates = mutableListOf<Delegate>()
                    favorites
                        .map {
                            delegates.add(
                                UpcomingViewData(
                                    id = it.id,
                                    image = it.headlinerImage,
                                    day = it.timeInMillis.dayFormatted(),
                                    month = it.timeInMillis.monthFormatted(),
                                    year = it.timeInMillis.yearFormatted(),
                                    name = it.name,
                                    time = it.timeInMillis.timeFormatted(),
                                    genre = it.genre
                                )
                            )
                        }

                    val NUMBER_OF_FIRST_BANNER_ROW = 3
                    for (item: Int in 0..(delegates.size / NUMBER_OF_FIRST_BANNER_ROW)) {
                        val height = if (item == 0) {
                            50
                        } else {
                            250
                        }
                        val adUnitId = if (item % 2 == 0) {
                            AdUnitIds.Type.FIRST_FAVORITE_LIST
                        } else {
                            AdUnitIds.Type.SECOND_FAVORITE_LIST
                        }
                        val adViewData = AdViewData(
                            businessModelFactoryProducer.getViewFactory(),
                            height,
                            adUnitId
                        )
                        delegates.safeInsert(NUMBER_OF_FIRST_BANNER_ROW * (item + 1), adViewData)
                    }

                    DelegateViewState(delegates)
                }
            }
            _stateFavorite.value = stateFavorite
        }
    }
}

class FavoritesViewModelFactory(
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            BusinessModelFactoryProducer::class.java,
            GetFavoriteConcertsUseCase::class.java,
            SettingsRepository::class.java
        ).newInstance(businessModelFactoryProducer, getFavoriteConcertsUseCase, settingsRepository)
    }
}
