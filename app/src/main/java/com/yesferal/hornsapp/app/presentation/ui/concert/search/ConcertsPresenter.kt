package com.yesferal.hornsapp.app.presentation.ui.concert.search

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter.CategoryViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter.FiltersViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase

class ConcertsPresenter(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : BasePresenter<ConcertsFragment>() {

    fun onViewCreated()  {
        getConcertsByCategoryUseCase(
            categoryKey = CategoryKey.ALL,
            onSuccess = { concerts ->
                val categories = listOf(
                    CategoryViewData("Lima", CategoryKey.LIVE),
                    CategoryViewData("Online", CategoryKey.ONLINE),
                    CategoryViewData("Metal", CategoryKey.METAL),
                    CategoryViewData("Rock", CategoryKey.ROCK)
                )

                val viewState = ConcertsViewState(
                    categories = FiltersViewData(categories),
                    concerts = concerts.map {
                        it.mapToConcertViewData()
                    }
                )

                view?.render(viewState)
            },
            onError = {
                val viewState = ConcertsViewState(
                    errorMessage = R.string.error_no_items,
                )

                view?.render(viewState)
            }
        )
    }

    fun onFilterClick(categoryViewData: CategoryViewData) {
        getConcertsByCategoryUseCase(
            categoryKey = categoryViewData.categoryKey,
            onSuccess = { concerts ->
                val viewState = ConcertsViewState(
                    concerts = concerts.map {
                        it.mapToConcertViewData()
                    }
                )

                view?.render(viewState)
            },
            onError = {
                val viewState = ConcertsViewState(
                    errorMessage = R.string.error_no_items,
                )

                view?.render(viewState)
            }
        )
    }
}