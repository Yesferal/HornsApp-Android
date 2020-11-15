package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import com.yesferal.hornsapp.app.R
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
        getConcertsByCategoryUseCase(categoryViewData.categoryKey)
    }

    private fun getConcertsByCategoryUseCase(categoryKey: CategoryKey) {
        getConcertsByCategoryUseCase(
            categoryKey = categoryKey,
            onSuccess = { concerts ->

                val categories = listOf(
                    CategoryViewData(CategoryKey.LIVE, "Lima", CategoryKey.LIVE == categoryKey),
                    CategoryViewData(CategoryKey.ONLINE, "Online", CategoryKey.ONLINE == categoryKey),
                    CategoryViewData(CategoryKey.METAL, "Metal", CategoryKey.METAL == categoryKey),
                    CategoryViewData(CategoryKey.ROCK, "Rock", CategoryKey.ROCK == categoryKey)
                )

                val viewState = UpcomingViewState(
                    categories = FiltersViewData(categories),
                    concerts = concerts.map {
                        ConcertViewData(
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

                view?.render(viewState)
            },
            onError = {
                val viewState = UpcomingViewState(
                    errorMessage = R.string.error_no_items,
                )

                view?.render(viewState)
            }
        )
    }
}