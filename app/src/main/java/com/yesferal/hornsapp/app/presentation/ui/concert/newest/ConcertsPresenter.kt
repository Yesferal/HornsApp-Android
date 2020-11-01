package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase

class ConcertsPresenter(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : BasePresenter<ConcertsFragment>() {

    fun onViewCreated()  {
        getConcertsByCategoryUseCase(
            categoryKey = CategoryKey.ALL.toString(),
            onSuccess = { concerts ->
                val viewState = ConcertsViewState(concerts = concerts.map {
                    it.mapToConcertViewData()
                })

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