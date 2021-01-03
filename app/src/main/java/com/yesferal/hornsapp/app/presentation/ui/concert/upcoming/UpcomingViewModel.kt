package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted
import com.yesferal.hornsapp.multitype.model.ViewHolderBinding

class UpcomingViewModel(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : ViewModel() {
    private val _state = MutableLiveData<UpcomingViewState>()

    val state: LiveData<UpcomingViewState>
        get() = _state

    init {
        getConcertsByCategoryUseCase(CategoryKey.ALL)
    }

    fun onFilterClick(categoryViewData: CategoryViewData) {
        if (categoryViewData.isSelected) {
            getConcertsByCategoryUseCase(CategoryKey.ALL)
        } else {
            getConcertsByCategoryUseCase(categoryViewData.categoryKey)
        }
    }

    private fun getConcertsByCategoryUseCase(categoryKey: CategoryKey) {
        val categories = listOf(
            CategoryViewData(CategoryKey.LIVE, "Lima", CategoryKey.LIVE == categoryKey),
            CategoryViewData(CategoryKey.ONLINE, "Online", CategoryKey.ONLINE == categoryKey),
            CategoryViewData(CategoryKey.METAL, "Metal", CategoryKey.METAL == categoryKey),
            CategoryViewData(CategoryKey.ROCK, "Rock", CategoryKey.ROCK == categoryKey)
        )

        getConcertsByCategoryUseCase(
            categoryKey = categoryKey,
            onSuccess = { concerts ->

                val items = mutableListOf<ViewHolderBinding>().apply {
                    add(FiltersViewData(categories))
                    addAll(
                        concerts
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

                _state.value = UpcomingViewState(
                    items = items.toList()
                )
            },
            onError = {
                _state.value = UpcomingViewState(
                    items = listOf(
                        FiltersViewData(categories),
                        ErrorViewData(
                            R.drawable.ic_music_note,
                            R.string.error_no_items
                        )
                    )
                )
            }
        )
    }
}

class UpcomingViewModelFactory(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsByCategoryUseCase::class.java
        ).newInstance(getConcertsByCategoryUseCase)
    }
}