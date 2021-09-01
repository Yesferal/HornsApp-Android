package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.filters.CategoryViewData
import com.yesferal.hornsapp.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted
import com.yesferal.hornsapp.multitype.abstraction.Delegate
import com.yesferal.hornsapp.multitype.delegate.RowDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpcomingViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _stateUpcoming = MutableLiveData<UpcomingViewState>()

    val stateUpcoming: LiveData<UpcomingViewState>
        get() = _stateUpcoming

    init {
        viewModelScope.launch {
            _stateUpcoming.value = getUpcomingConcertsWith(CategoryDrawer.Type.ALL.name)
        }
    }

    fun onCategoryClick(categoryViewData: CategoryViewData) {
        viewModelScope.launch {
            if (categoryViewData.isSelected) {
                _stateUpcoming.value = getUpcomingConcertsWith(CategoryDrawer.Type.ALL.name)
            } else {
                _stateUpcoming.value = getUpcomingConcertsWith(categoryViewData.categoryKey)
            }
        }
    }

    private suspend fun getUpcomingConcertsWith(
        categoryKey: String
    ) = withContext(Dispatchers.IO) {
        val categories = settingsRepository.getAppDrawer()?.categories?.map { category ->
            CategoryViewData(
                category.key.orEmpty(),
                category.title?.text.orEmpty(),
                categoryKey == category.key
            )
        } ?: listOf()

        val categoryHorizontalMargin = 16

        when (val result = getConcertsUseCase()) {
            is Result.Success -> {
                val concerts = result.value
                    .filter {
                        categoryKey == CategoryDrawer.Type.ALL.name ||
                                it.tags?.contains(categoryKey) == true
                    }

                if (concerts.isEmpty()) {
                    return@withContext UpcomingViewState(
                        items = listOf(
                            RowDelegate.Builder()
                                .addItems(categories)
                                .addHorizontalMargin(categoryHorizontalMargin)
                                .build(),
                            ErrorViewData(
                                R.drawable.ic_music_note,
                                R.string.error_no_items
                            )
                        )
                    )
                }

                val items = mutableListOf<Delegate>().apply {
                    add(
                        RowDelegate.Builder()
                            .addItems(categories)
                            .addHorizontalMargin(categoryHorizontalMargin)
                            .build()
                    )
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

                return@withContext UpcomingViewState(
                    items = items.toList()
                )
            }
            is Result.Error -> {
                return@withContext UpcomingViewState(
                    items = listOf(
                        RowDelegate.Builder()
                            .addItems(categories)
                            .addHorizontalMargin(categoryHorizontalMargin)
                            .build(),
                        ErrorViewData(
                            R.drawable.ic_music_note,
                            R.string.error_no_items
                        )
                    )
                )
            }
        }
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
