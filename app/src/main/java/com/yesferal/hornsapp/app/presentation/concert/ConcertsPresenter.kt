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
) : BasePresenter<ConcertsFragment, ConcertsViewData>() {

    fun onViewCreated() {
        getConcerts()
    }

    fun onRefresh() {
        render(ConcertsViewData(isLoading = true))
        getConcerts()
    }

    private fun getConcerts() {
        getConcertsUseCase(
            onSuccess = { concerts ->
                val categories = listOf(
                    Category(CategoryKey.ALL.toString(), "Todos"),
                    Category(CategoryKey.FAVORITE.toString(), "Favoritos"),
                    Category(CategoryKey.LIMA.toString(), "Lima"),
                    Category(CategoryKey.ONLINE.toString(), "Online"),
                    Category(CategoryKey.METAL.toString(), "Metal"),
                    Category(CategoryKey.ROCK.toString(), "Rock")
                )

                val viewData = ConcertsViewData(concerts, categories, categories[0], adManager.concertsAdView())
                render(viewData)
            },
            onError = {
                val viewData = ConcertsViewData(
                    errorMessage = R.string.error_default,
                    allowRetry = true
                )
                render(viewData)
            }
        )
    }

    fun onCategoryClick(category: Category) {
        getConcertsByCategoryUseCase(
            categoryKey = category._id,
            onSuccess = {
                val viewData = ConcertsViewData(
                    concerts = it,
                    selectedCategory = category)
                render(viewData)
            },
            onError = {
                val viewData = ConcertsViewData(
                    errorMessage = R.string.error_no_items,
                    selectedCategory = category)
                render(viewData)
            }
        )
    }

    override fun render(viewData: ConcertsViewData) {
        viewData.categories?.let { categories ->
            view?.showCategories(categories = categories)
        }
        viewData.selectedCategory?.let { category ->
            view?.showCategorySelected(category)
        }
        viewData.concerts?.let { concerts ->
            view?.showConcerts(concerts.take(20))
        }
        viewData.adView?.let { adView ->
            view?.showAd(adView)
        }

        viewData.errorMessage?.let {
            view?.showError(
                messageId =  viewData.errorMessage,
                allowRetry = viewData.allowRetry
            )
        }?: kotlin.run { view?.hideError() }

        if (viewData.isLoading) {
            view?.showProgress()
        } else {
            view?.hideProgress()
        }
    }
}