package com.yesferal.hornsapp.app.presentation.concert

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase

class ConcertsPresenter(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase,
    private val adManager: AdManager
) : BasePresenter<ConcertsFragment>() {

    fun onViewCreated() {
        getConcerts()
    }

    fun onRefresh() {
        view?.render(ConcertsViewState(isLoading = true))
        getConcerts()
    }

    private fun getConcerts() {
        getConcertsUseCase(
            onSuccess = { concerts ->
                val categories = listOf(
                    Category(CategoryKey.ALL.toString(), "Todos"),
                    Category(CategoryKey.FAVORITE.toString(), "Favoritos"),
                    Category(CategoryKey.LIVE.toString(), "Lima"),
                    Category(CategoryKey.ONLINE.toString(), "Online"),
                    Category(CategoryKey.METAL.toString(), "Metal"),
                    Category(CategoryKey.ROCK.toString(), "Rock")
                )

                val viewState = ConcertsViewState(
                    concerts = concerts.take(20),
                    categories = categories,
                    adView = adManager.concertsAdView())
                view?.render(viewState)
            },
            onError = {
                val viewState = ConcertsViewState(
                    errorMessage = R.string.error_default,
                    allowRetry = true
                )
                view?.render(viewState)
            }
        )
    }

    fun onCategoryClick(category: Category) {
        getConcertsByCategoryUseCase(
            categoryKey = category._id,
            onSuccess = {
                val viewState = ConcertsViewState(
                    concerts = it,
                    selectedCategory = category)
                view?.render(viewState)
            },
            onError = {
                val viewState = ConcertsViewState(
                    errorMessage = R.string.error_no_items,
                    selectedCategory = category)
                view?.render(viewState)
            }
        )
    }
}