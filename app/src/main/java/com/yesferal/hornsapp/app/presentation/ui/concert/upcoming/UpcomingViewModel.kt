package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.filters.CategoryViewData
import com.yesferal.hornsapp.core.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.core.domain.common.Result
import com.yesferal.hornsapp.core.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.core.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.core.domain.util.dayFormatted
import com.yesferal.hornsapp.core.domain.util.monthFormatted
import com.yesferal.hornsapp.core.domain.util.timeFormatted
import com.yesferal.hornsapp.core.domain.util.yearFormatted
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.delegate.RowDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpcomingViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _stateUpcoming = MutableLiveData<DelegateViewState>()
    val stateUpcoming: LiveData<DelegateViewState>
        get() = _stateUpcoming

    private lateinit var categoryDrawer: List<CategoryDrawer>

    init {
        viewModelScope.launch {
            delay(settingsRepository.screenDelay)
            settingsRepository.getCategoryDrawer().collect {
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
                _stateUpcoming.value = getUpcomingConcertsWith(categoryViewData.categoryKey)
            }
        }
    }

    private suspend fun getUpcomingConcertsWith(
        categoryKey: String
    ) = withContext(Dispatchers.IO) {
        val categories = categoryDrawer.map { category ->
                CategoryViewData(
                    category.key.orEmpty(),
                    category.title?.text.orEmpty(),
                    categoryKey == category.key
                )
            }

        when (val result = getConcertsUseCase()) {
            is Result.Success -> {
                val concerts = result.value
                    .filter {
                        categoryKey == CategoryDrawer.ALL ||
                                it.tags?.contains(categoryKey) == true
                    }

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

                val delegates = mutableListOf<Delegate>().apply {
                    add(mapCategories(categories))
                    addAll(concerts
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
                }

                return@withContext DelegateViewState(delegates.toList())
            }
            is Result.Error -> {
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
    private val getConcertsUseCase: GetConcertsUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            SettingsRepository::class.java
        ).newInstance(getConcertsUseCase, settingsRepository)
    }
}
