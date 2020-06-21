package com.yesferal.hornsapp.app.presentation.concert

import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.State
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase

class ConcertsPresenter(
    private val getConcertsUseCase: GetConcertsUseCase
) : BasePresenter<ConcertsFragment, ConcertViewData>() {

    override fun onViewCreated() {
        getConcertsUseCase(
            onSuccess = { list ->
                val viewData = ConcertViewData(list)
                val success = State.Success(viewData)
                render(state = success)
            },
            onError = {
                // TODO: ErrorHandler
            }
        )
    }

    override fun render(state: State<ConcertViewData>) {
        when(state) {
            is State.Success -> {
                view?.show(concerts = state.viewData.list)
            }
        }
    }
}