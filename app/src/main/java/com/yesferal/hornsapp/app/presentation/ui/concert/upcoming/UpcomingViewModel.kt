/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.common.extension.dayFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.monthFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.timeFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.yearFormatted
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.filters.CategoryViewData
import com.yesferal.hornsapp.core.domain.abstraction.DrawerRepository
import com.yesferal.hornsapp.core.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.core.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.core.domain.entity.drawer.ViewDrawer
import com.yesferal.hornsapp.core.domain.usecase.GetUpcomingConcertsUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.delegate.RowDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpcomingViewModel(
    private val getUpcomingConcertsUseCase: GetUpcomingConcertsUseCase,
    private val settingsRepository: SettingsRepository,
    private val drawerRepository: DrawerRepository
) : ViewModel() {

    private val _stateUpcoming = MutableLiveData<DelegateViewState>()
    val stateUpcoming: LiveData<DelegateViewState>
        get() = _stateUpcoming

    private lateinit var categoryDrawer: List<ViewDrawer>

    init {
        viewModelScope.launch {
            delay(settingsRepository.screenDelay)
            drawerRepository.getCategoryDrawer().collect {
                categoryDrawer = it
                onRender()
            }
        }
    }

    private fun onRender() {
        viewModelScope.launch {
            _stateUpcoming.value = getUpcomingConcertsWith(CategoryDrawer.ALL)
        }
    }

    fun onCategoryClick(categoryViewData: CategoryViewData) {
        viewModelScope.launch {
            if (categoryViewData.isSelected) {
                _stateUpcoming.value = getUpcomingConcertsWith(CategoryDrawer.ALL)
            } else {
                _stateUpcoming.value = getUpcomingConcertsWith(categoryViewData.condition)
            }
        }
    }

    private suspend fun getUpcomingConcertsWith(
        categoryCondition: String
    ) = withContext(Dispatchers.IO) {
        val categories = categoryDrawer.map { category ->
                CategoryViewData(
                    category.condition?.value.orEmpty(),
                    category.data?.title?.text.orEmpty(),
                    categoryCondition == category.condition?.value
                )
            }

        when (val result = getUpcomingConcertsUseCase(categoryCondition)) {
            is HaResult.Success -> {
                val concerts = result.value

                if (concerts.isEmpty()) {
                    return@withContext DelegateViewState(
                        delegates = listOf(
                            mapCategories(categories),
                            ErrorViewData(
                                R.drawable.ic_music_note,
                                R.string.error_no_items
                            )
                        )
                    )
                }

                var year = String()
                val delegates = mutableListOf<Delegate>().apply {
                    add(mapCategories(categories))
                }

                concerts
                    .map {
                        val concertYear = it.timeInMillis.yearFormatted()
                        if (concertYear != null && year != it.timeInMillis.yearFormatted()) {
                            year = concertYear
                            delegates.add(YearViewData(year))
                        }

                        delegates.add(UpcomingViewData(
                            id = it.id,
                            image = it.headlinerImage,
                            day = it.timeInMillis.dayFormatted(),
                            month = it.timeInMillis.monthFormatted(),
                            year = it.timeInMillis.yearFormatted(),
                            name = it.name,
                            time = it.timeInMillis.timeFormatted(),
                            genre = it.genre
                        ))
                    }

                return@withContext DelegateViewState(delegates.toList())
            }
            is HaResult.Error -> {
                return@withContext DelegateViewState(
                    delegates = listOf(
                        mapCategories(categories),
                        ErrorViewData(
                            R.drawable.ic_music_note,
                            R.string.error_no_items
                        )
                    )
                )
            }
        }
    }

    private fun mapCategories(categories: List<Delegate>): Delegate {
        return RowDelegate.Builder()
            .addItems(categories)
            .addHorizontalMargin(16)
            .build()
    }
}

class UpcomingViewModelFactory(
    private val getUpcomingConcertsUseCase: GetUpcomingConcertsUseCase,
    private val settingsRepository: SettingsRepository,
    private val drawerRepository: DrawerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetUpcomingConcertsUseCase::class.java,
            SettingsRepository::class.java,
            DrawerRepository::class.java
        ).newInstance(getUpcomingConcertsUseCase, settingsRepository, drawerRepository)
    }
}
