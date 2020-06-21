package com.yesferal.hornsapp.app.presentation.concert.detail

import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.usecase.GetConcertUseCase

class ConcertPresenter(
    private val getConcertUseCase: GetConcertUseCase
) : BasePresenter<ConcertFragment, ConcertViewData>() {

    fun onViewCreated(id: String) {
        getConcertUseCase(
            id,
            onSuccess = {
                val viewData = ConcertViewData(it)
                val success = ViewState.Success(viewData)
                render(state = success)
            },
            onError = {
                TODO("Not yet implemented")
            }
        )
    }

    override fun render(
        state: ViewState<ConcertViewData>
    ) {
        when(state) {
            is ViewState.Success -> {
                view?.show(state.viewData.concert)
            }
        }
    }
}