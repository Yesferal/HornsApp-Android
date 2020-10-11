package com.yesferal.hornsapp.app.presentation.concert.detail

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewEffect
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateFavoriteConcertUseCase

class ConcertPresenter(
    private val getConcertUseCase: GetConcertUseCase,
    private val adManager: AdManager,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase
) : BasePresenter<ConcertFragment>() {

    fun onViewCreated(id: String) {
        getConcertUseCase(
            id,
            onSuccess = {
                val viewState = ConcertViewState(
                    concert = it,
                    adView = adManager.concertDetailAdView()
                )
                view?.render(viewState)
            },
            onError = {
                view?.render(ConcertViewState(errorMessageId = R.string.error_default))
            }
        )
    }

    fun onFavoriteImageViewClick(
        concert: Concert,
        isChecked: Boolean
    ) {
        concert.isFavorite = isChecked
        updateFavoriteConcertUseCase(
            concert,
            onInsert = {
                view?.render(ViewEffect.Toast(R.string.add_to_favorite))
            },
            onRemove = {}
        )
    }
}