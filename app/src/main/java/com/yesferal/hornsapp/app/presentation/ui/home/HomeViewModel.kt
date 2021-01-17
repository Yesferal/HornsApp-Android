package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.lifecycle.*
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.NewestViewState
import com.yesferal.hornsapp.app.presentation.ui.concert.newest.TitleViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.FiltersViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewState
import com.yesferal.hornsapp.app.presentation.ui.favorite.FavoritesViewState
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
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
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase,
    private val adManager: AdManager
): ViewModel() {
    private val _state = MutableLiveData<HomeViewState>()

    val state: LiveData<HomeViewState>
        get() = _state

    init {
        getConcerts()
    }

    fun onRefresh() {
        _state.value = HomeViewState(isLoading = true)
        getConcerts()
    }

    private fun getConcerts() {
        getConcertsUseCase(
            onSuccess = {
                val titles = listOf("Novedades", "Proximos", "Favoritos")

                _state.value = HomeViewState(
                    fragmentTitles = titles,
                    concerts = it,
                    adViewData = adManager.concertsAdView())

                getUpcomingConcertsWith(CategoryKey.ALL)
                getNewestConcerts()
            },
            onError = {
                _state.value = HomeViewState(
                    errorMessage = R.string.error_default,
                    allowRetry = true
                )
            }
        )
    }

    // region Favorite
    private val _stateFavorite = MutableLiveData<FavoritesViewState>()

    val stateFavorite: LiveData<FavoritesViewState>
        get() = _stateFavorite

    fun getFavoriteConcerts() {
        viewModelScope.launch {
            val favorites = withContext(Dispatchers.IO) {
                getFavoriteConcertsUseCase()
            }

            if (favorites.isEmpty()) {
                _stateFavorite.value = FavoritesViewState(
                    items = listOf(
                        ErrorViewData(
                            R.drawable.ic_music_note,
                            R.string.error_no_favorite_yet
                        )
                    )
                )
            } else {
                _stateFavorite.value = withContext(Dispatchers.IO) {
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
        if (categoryViewData.isSelected) {
            getUpcomingConcertsWith(CategoryKey.ALL)
        } else {
            getUpcomingConcertsWith(categoryViewData.categoryKey)
        }
    }

    private fun getUpcomingConcertsWith(categoryKey: CategoryKey) {
        viewModelScope.launch {
            _stateUpcoming.value = withContext(Dispatchers.IO) {
                val categories = listOf(
                    CategoryViewData(CategoryKey.LIVE, "Lima", CategoryKey.LIVE == categoryKey),
                    CategoryViewData(CategoryKey.ONLINE, "Online", CategoryKey.ONLINE == categoryKey),
                    CategoryViewData(CategoryKey.METAL, "Metal", CategoryKey.METAL == categoryKey),
                    CategoryViewData(CategoryKey.ROCK, "Rock", CategoryKey.ROCK == categoryKey)
                )

                val concerts = _state.value?.concerts

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
                    addAll(
                        concerts
                            .filter {
                                categoryKey == CategoryKey.ALL ||
                                it.tags?.contains(categoryKey.toString()) == true
                            }
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

        }
    }
    // endregion

    // region Newest
    private val _stateNewest = MutableLiveData<NewestViewState>()

    val stateNewest: LiveData<NewestViewState>
        get() = _stateNewest

    private fun getNewestConcerts() {
        viewModelScope.launch {
            _stateNewest.value = withContext(Dispatchers.IO) {
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
        }
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
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase,
    private val adManager: AdManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            GetFavoriteConcertsUseCase::class.java,
            AdManager::class.java
        ).newInstance(getConcertsUseCase, getFavoriteConcertsUseCase, adManager)
    }
}