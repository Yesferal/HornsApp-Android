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
                val viewData = ConcertViewData(
                    concert = it,
                    adView = adManager.concertDetailAdView()
                )
                render(viewData)
            },
            onError = {
                render(ConcertViewData(errorMessage = R.string.error_default))
            }
        )
    }

    override fun render(
        viewData: ConcertViewData
    ) {
        viewData.concert?.let {
            view?.show(concert = it)
        }

        viewData.adView?.let {
            view?.showAd(it)
        }

        viewData.errorMessage?.let {
            view?.showError(
                messageId =  it
            )
        }

        if (viewData.isLoading) {
            view?.showProgress()
        } else {
            view?.hideProgress()
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