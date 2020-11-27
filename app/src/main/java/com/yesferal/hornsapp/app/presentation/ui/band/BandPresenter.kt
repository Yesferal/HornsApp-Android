package com.yesferal.hornsapp.app.presentation.ui.band

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BasePresenter
import com.yesferal.hornsapp.domain.usecase.GetBandUseCase

class BandPresenter(
    private val getBandUseCase: GetBandUseCase
) : BasePresenter<BandFragment>() {

    fun onViewCreated(id: String) {
        getBandUseCase(
            id,
            onSuccess = {
                val viewState = BandViewState(band = it)
                view?.render(viewState)
            },
            onError = {
                view?.render(BandViewState(errorMessageId = R.string.error_default))
            }
        )
    }
}