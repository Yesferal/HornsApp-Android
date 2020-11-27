package com.yesferal.hornsapp.app.presentation.ui.concert.detail

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.base.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.base.ViewEffect
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
                val concert = ConcertViewData(
                    it.id,
                    it.name,
                    it.description,
                    it.date,
                    it.dateTime,
                    it.day,
                    it.month,
                    it.trailerUrl,
                    it.facebookUrl,
                    it.isFavorite,
                    it.genre,
                    it.ticketingHost,
                    it.ticketingUrl,
                    it.venue
                )

                val bands = it.bands?.map { band ->
                    BandViewData(
                        band._id,
                        it.name,
                        band.membersImage,
                        band.genre
                    )
                }

                val viewState = ConcertViewState(
                    concert = concert,
                    bands = bands,
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
        concert: ConcertViewData,
        isChecked: Boolean
    ) {
        concert.isFavorite = isChecked
        updateFavoriteConcertUseCase(
            concert.isFavorite,
            concert.id,
            onInsert = {
                view?.render(ViewEffect.Toast(R.string.add_to_favorite))
            },
            onRemove = {}
        )
    }
}