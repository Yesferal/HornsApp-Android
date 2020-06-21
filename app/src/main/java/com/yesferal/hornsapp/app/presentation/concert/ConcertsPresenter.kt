package com.yesferal.hornsapp.app.presentation.concert

import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase

class ConcertsPresenter(
    private val getConcertsUseCase: GetConcertsUseCase
) : BasePresenter<ConcertsFragment, ConcertsViewData>() {

    fun onViewCreated() {
        getConcertsUseCase(
            onSuccess = { list ->
                val viewData = ConcertsViewData(list)
                val success = ViewState.Success(viewData)
                render(state = success)
            },
            onError = {
                TODO("Implement ErrorHandler")
            }
        )
    }

    override fun render(state: ViewState<ConcertsViewData>) {
        when(state) {
            is ViewState.Success -> {
                view?.show(concerts = state.viewData.concerts)
            }
        }
    }
}