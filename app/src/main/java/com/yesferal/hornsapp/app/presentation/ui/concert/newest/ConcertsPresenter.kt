package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase

class ConcertsPresenter(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : BasePresenter<ConcertsFragment>() {

    fun onViewCreated(categoryId: String)  {
        getConcertsByCategoryUseCase(
            categoryKey = categoryId,
            onSuccess = {
                val viewState = ConcertsViewState(concerts = it)

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