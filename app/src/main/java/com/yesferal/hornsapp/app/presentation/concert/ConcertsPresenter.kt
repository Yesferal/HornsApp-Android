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
                    Category(CategoryKey.ALL.toString(), "Todos", "https://c4.wallpaperflare.com/wallpaper/850/581/197/hands-people-heavy-metal-concerts-wallpaper-preview.jpg"),
                    Category(CategoryKey.FAVORITE.toString(), "Favoritos", "https://c4.wallpaperflare.com/wallpaper/850/581/197/hands-people-heavy-metal-concerts-wallpaper-preview.jpg"),
                    Category(CategoryKey.LIVE.toString(), "Lima", "https://media.altpress.com/uploads/2020/03/concert-crowd.jpeg"),
                    Category(CategoryKey.ONLINE.toString(), "Online", "https://www.fmpalihue.com/inicio/wp-content/uploads/2020/06/conciertos-streaming.jpg"),
                    Category(CategoryKey.METAL.toString(), "Metal", "https://i.pinimg.com/originals/53/4e/2d/534e2df6ce8166eee010ef7282b4491b.jpg"),
                    Category(CategoryKey.ROCK.toString(), "Rock", "https://pbs.twimg.com/profile_images/761544198420127744/9T__51m4_400x400.jpg")
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