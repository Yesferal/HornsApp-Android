package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.lifecycle.*
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestViewState
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.FiltersViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewState
import com.yesferal.hornsapp.app.presentation.ui.favorite.FavoritesViewState
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.usecase.GetFavoriteConcertsUseCase
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted
import com.yesferal.hornsapp.multitype.model.ViewHolderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class HomeViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase
): ViewModel() {
    private val _state = MutableLiveData<HomeViewState>()

    val state: LiveData<HomeViewState>
        get() = _state

    init {
        updateViews()
    }

    fun onRefresh() {
        _state.value = HomeViewState(isLoading = true)
        updateViews()
    }

    private fun updateViews() {
        viewModelScope.launch {
            _state.value = getConcerts()
            _stateUpcoming.value = getUpcomingConcertsWith(CategoryKey.ALL)
            _stateNewest.value = getNewestConcerts()
        }
    }

    private suspend fun getConcerts() = withContext(Dispatchers.IO) {
        when (val result = getConcertsUseCase()) {
            is Result.Success -> {
                val titles = listOf("Novedades", "Proximos", "Favoritos")

                HomeViewState(
                        fragmentTitles = titles,
                        concerts = result.value
                )
            }
            is Result.Error -> {
                HomeViewState(
                        errorMessage = R.string.error_default,
                        allowRetry = true
                )
            }
        }
    }

    // region Favorite
    private val _stateFavorite = MutableLiveData<FavoritesViewState>()

    val stateFavorite: LiveData<FavoritesViewState>
        get() = _stateFavorite

    fun getFavoriteConcerts() {
        viewModelScope.launch {
            _stateFavorite.value = withContext(Dispatchers.IO) {
                val favorites = getFavoriteConcertsUseCase()

                if (favorites.isEmpty()) {
                    FavoritesViewState(
                        items = listOf(
                            ErrorViewData(
                                    R.drawable.ic_music_note,
                                    R.string.error_no_favorite_yet
                            )
                        )
                    )
                } else {
                    FavoritesViewState(items = favorites
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
            }
        }
    }
    // endregion

    // region Upcoming
    private val _stateUpcoming = MutableLiveData<UpcomingViewState>()

    val stateUpcoming: LiveData<UpcomingViewState>
        get() = _stateUpcoming

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

        val concerts = _state.value?.concerts
            ?.filter {
                categoryKey == CategoryKey.ALL ||
                        it.tags?.contains(categoryKey.toString()) == true
            }

        if (concerts == null || concerts.isEmpty()) {
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
    // endregion

    // region Newest
    private val _stateNewest = MutableLiveData<NewestViewState>()

    val stateNewest: LiveData<NewestViewState>
        get() = _stateNewest

    private suspend fun getNewestConcerts() = withContext(Dispatchers.IO) {
        val concerts = _state.value?.concerts

        if (concerts == null || concerts.isEmpty()) {
            return@withContext NewestViewState(
                errorMessage = R.string.error_no_items,
            )
        }

        val views = mutableListOf<ViewHolderBinding>()
        val concertReversed = concerts.reversed()
        val firstConcert = concertReversed.toMutableList().removeFirst()
        views.add(
            UpcomingViewData(
                id = firstConcert.id,
                image = firstConcert.headlinerImage,
                day = firstConcert.dateTime?.dayFormatted(),
                month = firstConcert.dateTime?.monthFormatted(),
                year = firstConcert.dateTime?.yearFormatted(),
                name = firstConcert.name,
                time = firstConcert.dateTime?.timeFormatted(),
                genre = firstConcert.genre
            )
        )

        val thisYear = Calendar.getInstance().get(Calendar.YEAR)
        views.insertElementByYear(concertReversed, thisYear)

        val nextYear = thisYear + 1
        views.insertElementByYear(concertReversed, nextYear)

        return@withContext NewestViewState(views)
    }

    private fun MutableList<ViewHolderBinding>.insertElementByYear(
        concerts: List<Concert>,
        year: Int
    ) {
        val views = concerts
            .filter { year.toString() == it.dateTime?.yearFormatted() }
            .take(3)
            .sortedWith(compareBy { it.dateTime?.time })
            .map { concert ->
                NewestViewData(
                    id = concert.id,
                    day = concert.dateTime?.dayFormatted(),
                    month = concert.dateTime?.monthFormatted(),
                    name = concert.name,
                    ticketingHostName = concert.ticketingHost
                )
            }

        if (views.isEmpty()) { return }

        this.add(TitleViewData("#$year"))
        this.addAll(views)
    }
    // endregion
}

class HomeViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            GetFavoriteConcertsUseCase::class.java
        ).newInstance(getConcertsUseCase, getFavoriteConcertsUseCase)
    }
}