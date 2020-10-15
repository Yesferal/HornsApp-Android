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
                val categories = listOf(
                    Category(CategoryKey.ALL.toString(), "Todos"),
                    Category(CategoryKey.LIVE.toString(), "Lima"),
                    Category(CategoryKey.ONLINE.toString(), "Online"),
                    Category(CategoryKey.METAL.toString(), "Metal"),
                    Category(CategoryKey.ROCK.toString(), "Rock")
                )

                val viewState = HomeViewState(
                    categories = categories,
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