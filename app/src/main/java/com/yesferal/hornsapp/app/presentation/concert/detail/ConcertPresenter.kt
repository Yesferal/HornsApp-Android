package com.yesferal.hornsapp.app.presentation.concert.detail

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateFavoriteConcertUseCase

class ConcertPresenter(
    private val getConcertUseCase: GetConcertUseCase,
    private val adManager: AdManager,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
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
            }
        )
    }

    override fun render(
        state: ViewState<ConcertViewData>
    ) {
        when(state) {
            is ViewState.Success -> {
                view?.show(concert = state.viewData.concert)
                view?.show(adView = adManager.concertDetailAdView())
                view?.hideProgress()
            }
            is ViewState.Progress -> {
                view?.showProgress()
            }
            is ViewState.Error-> {
                view?.show(error = state.message)
                view?.hideProgress()
            }
        }
    }

    fun onFavoriteImageViewClick(
        concert: Concert,
        isChecked: Boolean
    ) {
        concert.isFavorite = isChecked
        updateFavoriteConcertUseCase(
            concert,
            onInsert = {
                view?.showToast(R.string.add_to_favorite)
            },
            onRemove = {}
        )
    }
}