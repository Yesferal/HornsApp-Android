package com.yesferal.hornsapp.app.presentation.concert.detail

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.usecase.GetConcertUseCase

class ConcertPresenter(
    private val getConcertUseCase: GetConcertUseCase,
    private val adManager: AdManager
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
                render(ViewState.Error(R.string.error_default))
                //TODO("Not yet implemented")
            }
        )
    }

    override fun render(
        state: ViewState<ConcertViewData>
    ) {
        when(state) {
            is ViewState.Success -> {
                view?.hideProgress()
                view?.show(concert = state.viewData.concert)
                view?.show(adView = adManager.concertDetailAdView())
            }
            is ViewState.Progress -> {
                view?.showProgress()
            }
            is ViewState.Error-> {
                view?.hideProgress()
                view?.show(error = state.message)
            }
        }
    }
}