package com.yesferal.hornsapp.app.presentation.ui.home

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase

class HomePresenter(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val adManager: AdManager
) : BasePresenter<HomeFragment>() {

    fun onViewCreated() {
        getConcerts()
    }

    fun onRefresh() {
        view?.render(HomeViewState(isLoading = true))
        getConcerts()
    }

    private fun getConcerts() {
        getConcertsUseCase(
            onSuccess = {
                val titles = listOf("Novedades", "Buscar", "Favoritos")

                val viewState = HomeViewState(
                    fragmentTitles = titles,
                    adView = adManager.concertsAdView())

                view?.render(viewState)
            },
            onError = {
                val viewState = HomeViewState(
                    errorMessage = R.string.error_default,
                    allowRetry = true
                )

                view?.render(viewState)
            }
        )
    }
}