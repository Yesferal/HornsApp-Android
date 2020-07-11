package com.yesferal.hornsapp.app.presentation.concert

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateFavoriteConcertUseCase

class ConcertsPresenter(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase,
    private val adManager: AdManager
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
                view?.show(adView = adManager.concertsAdView())
            }
        }
    }

    fun onFavoriteButtonClick(concert: Concert) {
        updateFavoriteConcertUseCase(
            concert,
            onInsert = {
                view?.showToast(R.string.add_to_favorite)
            },
            onRemove = {
                view?.showToast(R.string.remove_from_favorite)
            }
        )
    }
}