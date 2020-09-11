package com.yesferal.hornsapp.app.presentation.concert

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase

class ConcertsPresenter(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val adManager: AdManager
) : BasePresenter<ConcertsFragment, ConcertsViewData>() {

    fun onViewCreated() {
        getConcerts()
    }

    fun onRefresh() {
        render(ViewState.Progress)
        getConcerts()
    }

    private fun getConcerts() {
        getConcertsUseCase(
            onSuccess = { list ->
                val viewData = ConcertsViewData(list.take(5))
                val success = ViewState.Success(viewData)
                render(state = success)
            },
            onError = {
                render(ViewState.Error(R.string.error_default))
            }
        )
    }

    override fun render(state: ViewState<ConcertsViewData>) {
        when(state) {
            is ViewState.Success -> {
                view?.hideProgress()
                view?.hideError()
                view?.show(concerts = state.viewData.concerts)
                view?.show(adView = adManager.concertsAdView())
            }
            is ViewState.Progress -> {
                view?.showProgress()
            }
            is ViewState.Error-> {
                view?.hideProgress()
                view?.showError(messageId =  state.message)
            }
        }
    }
}