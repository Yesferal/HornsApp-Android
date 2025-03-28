/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.BusinessModelFactoryProducer
import com.yesferal.hornsapp.app.presentation.common.delegate.DelegateViewState
import com.yesferal.hornsapp.app.presentation.common.delegate.includeAdViewSection
import com.yesferal.hornsapp.app.presentation.common.delegate.includeIconHomeCardSection
import com.yesferal.hornsapp.app.presentation.common.delegate.includeImageHomeCardSection
import com.yesferal.hornsapp.app.presentation.common.extension.addVerticalDivider
import com.yesferal.hornsapp.app.presentation.common.extension.dateTimeFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.dayFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.monthFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.timeFormatted
import com.yesferal.hornsapp.app.presentation.common.extension.yearFormatted
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.core.domain.abstraction.Logger
import com.yesferal.hornsapp.core.domain.abstraction.RenderRepository
import com.yesferal.hornsapp.core.domain.entity.Concert
import com.yesferal.hornsapp.core.domain.entity.render.ChildrenRender
import com.yesferal.hornsapp.core.domain.entity.render.ViewRender
import com.yesferal.hornsapp.core.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.delegate.RowDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewestViewModel(
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val getConcertsUseCase: GetConcertsUseCase,
    private val renderRepository: RenderRepository,
    private val logger: Logger
) : ViewModel() {

    private val _stateNewest = MutableLiveData<DelegateViewState>()
    val stateNewest: LiveData<DelegateViewState>
        get() = _stateNewest

    init {
        viewModelScope.launch {
            renderRepository.getNewestRender().collect {
                onRender(it)
            }
        }
    }

    private fun onRender(newestDrawer: List<ViewRender>) {
        viewModelScope.launch {
            _stateNewest.value = getNewestConcerts(newestDrawer)
        }
    }

    private suspend fun getNewestConcerts(newestDrawer: List<ViewRender>) =
        withContext(Dispatchers.IO) {
            when (val result = getConcertsUseCase()) {
                is HaResult.Success -> {
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
                            ViewRender.Type.ROW_VIEW -> {
                                delegates.includeCarouselSection(concerts, it)
                            }
                            ViewRender.Type.COLUMN_VIEW -> {
                                delegates.includeVerticalSection(concerts, it)
                            }
                            ViewRender.Type.ICON_CARD_VIEW -> {
                                delegates.includeIconHomeCardSection(it)
                            }
                            ViewRender.Type.CARD_VIEW -> {
                                delegates.includeImageHomeCardSection(it)
                            }
                            ViewRender.Type.AD_VIEW -> {
                                delegates.includeAdViewSection(businessModelFactoryProducer, it)
                            }
                            else -> {
                                return@forEach
                            }
                        }
                    }

                    return@withContext DelegateViewState(delegates)
                }
                is HaResult.Error -> {
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
        screenDrawer: ViewRender
    ) {
        val delegates = getConcertDelegates(concerts, screenDrawer)

        if (delegates.isEmpty()) {
            return
        }

        this.add(
            RowDelegate.Builder().addItems(delegates).addBackground(R.color.background)
                .addElevation(4F).build()
        )
        this.addVerticalDivider(24)
    }

    private fun MutableList<Delegate>.includeVerticalSection(
        concerts: List<Concert>,
        screenDrawer: ViewRender
    ) {
        val delegates = getConcertDelegates(concerts, screenDrawer)

        if (delegates.isEmpty()) {
            return
        }

        this.add(
            TitleViewData(
                screenDrawer.data?.title?.text,
                screenDrawer.data?.subtitle?.text,
                screenDrawer.navigation,
                screenDrawer.data?.icon
            )
        )
        this.addAll(delegates)
        this.addVerticalDivider(24)
    }

    private fun getConcertDelegates(
        concerts: List<Concert>,
        screenDrawer: ViewRender
    ): List<Delegate> {
        var children = concerts
            .filter { screenDrawer.children?.values?.contains(it.id) == true }

        return when (screenDrawer.children?.type) {
            ChildrenRender.Type.CAROUSEL_CARD_VIEW -> {
                if (children.isEmpty()) {
                    children = mapChildrenConcerts(
                        concerts.reversed(),
                        screenDrawer
                    )
                }
                children.map {
                    CarouselViewData(
                        id = it.id,
                        name = it.name,
                        time = it.timeInMillis.dateTimeFormatted(),
                        headlinerName = it.headlinerName,
                        headlinerUrl = it.headlinerImageUrl,
                        ticketingName = it.ticketingName,
                        ticketingUrl = it.ticketingUrl,
                    )
                }
            }
            ChildrenRender.Type.UPCOMING_CARD_VIEW -> {
                if (children.isEmpty()) {
                    children = mapChildrenConcerts(
                        concerts.sortedWith(compareBy { it.timeInMillis }),
                        screenDrawer
                    )
                }
                children.map {
                    NewestViewData(
                        id = it.id,
                        day = it.timeInMillis.dayFormatted(),
                        month = it.timeInMillis.monthFormatted(),
                        name = it.name,
                        ticketingHostName = it.ticketingName
                    )
                }
            }
            ChildrenRender.Type.UPCOMING_IMAGE_CARD_VIEW -> {
                if (children.isEmpty()) {
                    children = mapChildrenConcerts(
                        concerts.sortedWith(compareBy { it.timeInMillis }),
                        screenDrawer
                    )
                }
                children.map {
                    UpcomingViewData(
                        id = it.id,
                        image = it.headlinerImageUrl,
                        day = it.timeInMillis.dayFormatted(),
                        month = it.timeInMillis.monthFormatted(),
                        year = it.timeInMillis.yearFormatted(),
                        name = it.name,
                        time = it.timeInMillis.timeFormatted(),
                        headlinerName = it.headlinerName
                    )
                }
            }
            else -> listOf()
        }
    }

    private fun mapChildrenConcerts(
        concerts: List<Concert>,
        screenDrawer: ViewRender
    ): List<Concert> {
        return concerts
            .filter {
                screenDrawer.children?.filter?.let { filter ->
                    it.tags?.contains(filter) == true
                }?: true
            }
            .take(screenDrawer.children?.take ?: Int.MAX_VALUE)
    }
}

class NewestViewModelFactory(
    private val businessModelFactoryProducer: BusinessModelFactoryProducer,
    private val getConcertsUseCase: GetConcertsUseCase,
    private val drawerRepository: RenderRepository,
    private val logger: Logger
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            BusinessModelFactoryProducer::class.java,
            GetConcertsUseCase::class.java,
            RenderRepository::class.java,
            Logger::class.java
        ).newInstance(businessModelFactoryProducer, getConcertsUseCase, drawerRepository, logger)
    }
}
