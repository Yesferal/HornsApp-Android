package com.yesferal.hornsapp.app.presentation.item

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.app.presentation.item.adapter.mapToItem
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase

class ItemsPresenter(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : BasePresenter<ItemsFragment, ItemsViewData>() {

    fun onViewCreated(categoryKey: String) {
        getConcertsByCategoryUseCase(
            categoryKey,
            onSuccess = { concerts ->
                val items = concerts.map { it.mapToItem() }
                val viewData = ItemsViewData(items)
                val success = ViewState.Success(viewData)
                render(state = success)
            },
            onError = {
                val error = when(categoryKey) {
                    CategoryKey.FAVORITE.toString() -> {
                        R.string.no_favorite_yet_error
                    }
                    else -> { R.string.no_items_error }
                }
                render(ViewState.Error(error))
                // TODO("Implement ErrorHandler")
            }
        )
    }

    override fun render(state: ViewState<ItemsViewData>) {
        when(state) {
            is ViewState.Success -> {
                view?.hideProgress()
                view?.show(items = state.viewData.concerts)
            }
            is ViewState.Progress -> {
                view?.showProgress()
            }
            is ViewState.Error-> {
                view?.hideProgress()
                view?.show(error = state.message)
            }
        }
    }
}