package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted
import com.yesferal.hornsapp.multitype.model.ViewHolderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpcomingViewModel(
    private val getConcertsUseCase: GetConcertsUseCase
) : ViewModel() {
    private val _stateUpcoming = MutableLiveData<UpcomingViewState>()

    val stateUpcoming: LiveData<UpcomingViewState>
        get() = _stateUpcoming

    init {
        viewModelScope.launch {
            _stateUpcoming.value = getUpcomingConcertsWith(CategoryKey.ALL)
        }
    }

    fun onFilterClick(categoryViewData: CategoryViewData) {
        viewModelScope.launch {
            if (categoryViewData.isSelected) {
                _stateUpcoming.value = getUpcomingConcertsWith(CategoryKey.ALL)
            } else {
                _stateUpcoming.value = getUpcomingConcertsWith(categoryViewData.categoryKey)
            }
        }
    }

    private suspend fun getUpcomingConcertsWith(
        categoryKey: CategoryKey
    ) = withContext(Dispatchers.IO) {
        val categories = listOf(
            CategoryViewData(CategoryKey.LIVE, "Lima", CategoryKey.LIVE == categoryKey),
            CategoryViewData(CategoryKey.ONLINE, "Online", CategoryKey.ONLINE == categoryKey),
            CategoryViewData(CategoryKey.METAL, "Metal", CategoryKey.METAL == categoryKey),
            CategoryViewData(CategoryKey.ROCK, "Rock", CategoryKey.ROCK == categoryKey)
        )

        when (val result = getConcertsUseCase()) {
            is Result.Success -> {
                val concerts = result.value
                    .filter {
                        categoryKey == CategoryKey.ALL ||
                                it.tags?.contains(categoryKey.toString()) == true
                    }

                if (concerts.isEmpty()) {
                    return@withContext UpcomingViewState(
                        items = listOf(
                            FiltersViewData(categories),
                            ErrorViewData(
                                R.drawable.ic_music_note,
                                R.string.error_no_items
                            )
                        )
                    )
                }

                val items = mutableListOf<ViewHolderBinding>().apply {
                    add(FiltersViewData(categories))
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
                        FiltersViewData(categories),
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
    private val getConcertsUseCase: GetConcertsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java
        ).newInstance(getConcertsUseCase)
    }
}
