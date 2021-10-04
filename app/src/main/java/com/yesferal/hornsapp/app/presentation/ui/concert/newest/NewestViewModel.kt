package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.common.extension.addVerticalDivider
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.delegate.RowDelegate
import com.yesferal.hornsapp.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.entity.drawer.ConditionDrawer
import com.yesferal.hornsapp.domain.entity.drawer.ScreenDrawer
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.util.dateTimeFormatted
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder
import java.util.*

class NewestViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _stateNewest = MutableLiveData<DelegateViewState>()
    val stateNewest: LiveData<DelegateViewState>
        get() = _stateNewest

    init {
        viewModelScope.launch {
            settingsRepository.getNewestDrawer().collect {
                onRender(it)
            }
        }
    }

    private fun onRender(newestDrawer: List<ScreenDrawer>) {
        viewModelScope.launch {
            _stateNewest.value = getNewestConcerts(newestDrawer)
        }
    }

    private suspend fun getNewestConcerts(newestDrawer: List<ScreenDrawer>) =
        withContext(Dispatchers.IO) {
            when (val result = getConcertsUseCase()) {
                is Result.Success -> {
                    val concerts = result.value

                    if (concerts.isEmpty()) {
                        return@withContext DelegateViewState(
                            delegates = listOf(
                                ErrorViewData(
                                    R.drawable.ic_music_note,
                                    R.string.error_no_items
                                )
                            )
                        )
                    }

                    val delegates = mutableListOf<Delegate>()
                    newestDrawer.forEach {
                        when (it.type) {
                            ScreenDrawer.Type.CAROUSEL_VIEW -> {
                                delegates.includeCarouselSection(concerts, it)
                            }
                            ScreenDrawer.Type.VERTICAL_LIST_VIEW -> {
                                delegates.includeVerticalSection(concerts, it)
                            }
                            ScreenDrawer.Type.HOME_CARD_VIEW -> {
                                delegates.includeHomeCardSection(it)
                            }
                            else -> {
                                return@forEach
                            }
                        }
                    }

                    return@withContext DelegateViewState(delegates)
                }
                is Result.Error -> {
                    return@withContext DelegateViewState(
                        delegates = listOf(
                            ErrorViewData(
                                R.drawable.ic_music_note,
                                R.string.error_no_items
                            )
                        )
                    )
                }
            }
        }

    private fun MutableList<Delegate>.includeCarouselSection(
        concerts: List<Concert>,
        screenDrawer: ScreenDrawer
    ) {
        val delegates = getConcertDelegates(concerts, screenDrawer)

        this.add(
            RowDelegate.Builder().addItems(delegates).addBackground(R.color.background)
                .addElevation(4F).build()
        )
        this.addVerticalDivider(24)
    }

    private fun MutableList<Delegate>.includeVerticalSection(
        concerts: List<Concert>,
        screenDrawer: ScreenDrawer
    ) {
        val delegates = getConcertDelegates(concerts, screenDrawer)

        if (delegates.isEmpty()) {
            return
        }

        val subtitle = screenDrawer.condition?.value?.let {
            StringBuilder().append("#").append(it).toString()
        }

        this.add(TitleViewData(screenDrawer.title?.text, subtitle))
        this.addAll(delegates)
        this.addVerticalDivider(24)
    }

    private fun getConcertDelegates(
        concerts: List<Concert>,
        screenDrawer: ScreenDrawer
    ): List<Delegate> {
        return when (screenDrawer.condition?.type) {
            ConditionDrawer.Type.SORT_BY_UPCOMING_DATE -> {
                sortByUpcomingDate(concerts, screenDrawer)
            }
            ConditionDrawer.Type.FILTER_BY_CATEGORY -> {
                filterByCategory(concerts, screenDrawer)
            }
            ConditionDrawer.Type.SORT_BY_NEWEST_DATE -> {
                sortByNewestDate(concerts, screenDrawer)
            }
            else -> listOf()
        }
    }

    private fun sortByNewestDate(
        concerts: List<Concert>,
        screenDrawer: ScreenDrawer
    ): List<Delegate> {
        return concerts.reversed()
            .take(screenDrawer.condition?.count ?: Int.MAX_VALUE)
            .map {
                CarouselViewData(
                    id = it.id,
                    image = it.headlinerImage,
                    name = it.name,
                    time = it.dateTime?.dateTimeFormatted(),
                    genre = it.genre,
                    ticketingUrl = it.ticketingUrl,
                    ticketingHost = it.ticketingHost
                )
            }
    }

    private fun sortByUpcomingDate(
        concerts: List<Concert>,
        screenDrawer: ScreenDrawer
    ): List<Delegate> {
        return concerts
            .sortedWith(compareBy { it.dateTime?.time })
            .take(screenDrawer.condition?.count ?: Int.MAX_VALUE)
            .map { concert ->
                NewestViewData(
                    id = concert.id,
                    day = concert.dateTime?.dayFormatted(),
                    month = concert.dateTime?.monthFormatted(),
                    name = concert.name,
                    ticketingHostName = concert.ticketingHost
                )
            }
    }

    private fun filterByCategory(
        concerts: List<Concert>,
        screenDrawer: ScreenDrawer
    ): List<Delegate> {
        return concerts.filter { it.tags?.contains(screenDrawer.condition?.value) == true }
            .take(screenDrawer.condition?.count ?: Int.MAX_VALUE)
            .map { concert ->
                UpcomingViewData(
                    id = concert.id,
                    image = concert.headlinerImage,
                    day = concert.dateTime?.dayFormatted(),
                    month = concert.dateTime?.monthFormatted(),
                    year = concert.dateTime?.yearFormatted(),
                    name = concert.name,
                    time = concert.dateTime?.timeFormatted(),
                    genre = concert.genre
                )
            }
    }

    private fun MutableList<Delegate>.includeHomeCardSection(
        screenDrawer: ScreenDrawer
    ) {
        this.add(HomeCardViewData(screenDrawer.title?.text, screenDrawer.subtitle?.text))
        this.addVerticalDivider(24)
    }
}

class NewestViewModelFactory(
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
