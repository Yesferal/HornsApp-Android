package com.yesferal.hornsapp.app.presentation.concert

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.framework.adMob.AdManager
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateFavoriteConcertUseCase

class ConcertsPresenter(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val updateFavoriteConcertUseCase: UpdateFavoriteConcertUseCase,
    private val adManager: AdManager
) : BasePresenter<ConcertsFragment, ConcertsViewData>() {

    fun onViewCreated() {
        getConcerts()
    }

    fun onRefresh() {
        render(ViewState.Progress)
        getConcerts()
    }

    private fun getConcerts() {
        getConcertsUseCase(
            onSuccess = { list ->
                val viewData = ConcertsViewData(list.take(5))
                val success = ViewState.Success(viewData)
                render(state = success)
            },
            onError = {
                render(ViewState.Error(R.string.error_default))
                // TODO("Implement ErrorHandler")
            }
        )
    }

    override fun render(state: ViewState<ConcertsViewData>) {
        when(state) {
            is ViewState.Success -> {
                view?.hideProgress()
                view?.hideError()
                view?.show(concerts = state.viewData.concerts)
                view?.show(adView = adManager.concertsAdView())
                // TODO ("Create GetCategoryUseCase(...)")
                val categories = listOf(
                    Category(CategoryKey.FAVORITE.toString(), "Favoritos", "https://c4.wallpaperflare.com/wallpaper/850/581/197/hands-people-heavy-metal-concerts-wallpaper-preview.jpg"),
                    Category(CategoryKey.LIVE.toString(), "Presenciales", "https://media.altpress.com/uploads/2020/03/concert-crowd.jpeg"),
                    Category(CategoryKey.VIRTUAL.toString(), "Virtuales", "https://www.fmpalihue.com/inicio/wp-content/uploads/2020/06/conciertos-streaming.jpg")
                )
                view?.showCategories(categories)
            }
            is ViewState.Progress -> {
                view?.showProgress()
            }
            is ViewState.Error-> {
                view?.hideProgress()
                view?.showError(messageId =  state.message)
            }
        }
    }

    fun onFavoriteButtonClick(concert: Concert) {
        updateFavoriteConcertUseCase(
            concert,
            onInsert = {
                view?.showToast(R.string.add_to_favorite)
            },
            onRemove = {
                view?.showToast(R.string.remove_from_favorite)
            }
        )
    }
}