package com.yesferal.hornsapp.app.presentation.band

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.domain.usecase.GetBandUseCase

class BandPresenter(
    private val getBandUseCase: GetBandUseCase
) : BasePresenter<BandFragment, BandViewData>(){

    fun onViewCreated(id: String) {
        getBandUseCase(
            id,
            onSuccess = {
                val viewData = BandViewData(band = it)
                render(viewData)
            },
            onError = {
                render(BandViewData(errorMessageId = R.string.error_default))
            }
        )
    }

    override fun render(viewData: BandViewData) {
        viewData.band?.let {
            view?.show(band = it)
        }

        viewData.errorMessageId?.let {
            view?.showError(messageId = it)
        }

        if (viewData.isLoading) {
            view?.showProgress()
        } else {
            view?.hideProgress()
        }
    }
}