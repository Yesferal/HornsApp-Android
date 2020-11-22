package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.common.ui.BasePresenter
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase

class UpcomingPresenter(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : BasePresenter<ConcertsFragment>() {

    fun onViewCreated()  {
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

                val items = mutableListOf<ViewData>().apply {
                    add(FiltersViewData(categories))
                    addAll(
                        concerts.map {
                            UpcomingViewData(
                                id = it.id,
                                image = it.headlinerImage,
                                day = it.day,
                                month = it.month,
                                year = it.year.toString(),
                                name = it.name,
                                time = it.time,
                                genre = it.genre
                            )
                        }
                    )
                }

                val viewState = UpcomingViewState(
                    items = items.toList()
                )

                view?.render(viewState)
            },
            onError = {
                val viewState = UpcomingViewState(
                    items = listOf(
                        FiltersViewData(categories),
                        ErrorViewData(
                            R.drawable.ic_music_note,
                            R.string.error_no_items
                        )
                    )
                )

                view?.render(viewState)
            }
        )
    }
}