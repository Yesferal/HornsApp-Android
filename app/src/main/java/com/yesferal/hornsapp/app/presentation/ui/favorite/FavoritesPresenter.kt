package com.yesferal.hornsapp.app.presentation.ui.favorite

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.ui.concert.search.ConcertsViewState
import com.yesferal.hornsapp.app.presentation.ui.concert.search.mapToConcertViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase

class FavoritesPresenter(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : BasePresenter<FavoritesFragment>() {
    fun onViewCreated()  {
        getConcertsByCategoryUseCase(
            categoryKey = CategoryKey.FAVORITE.toString(),
            onSuccess = { concerts ->
                val viewState = ConcertsViewState(concerts = concerts.map {
                    it.mapToConcertViewData()
                })

                view?.render(viewState)
            },
            onError = {
                val viewState = ConcertsViewState(
                    errorMessage = R.string.error_no_favorite_yet,
                )

                view?.render(viewState)
            }
        )
    }
}