package com.yesferal.hornsapp.app.presentation.item

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
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
                render(viewData)
            },
            onError = {
                val error = when(categoryKey) {
                    CategoryKey.FAVORITE.toString() -> {
                        R.string.error_no_favorite_yet
                    }
                    else -> { R.string.error_no_items }
                }
                render(ItemsViewData(errorMessage = error))
            }
        )
    }

    override fun render(viewData: ItemsViewData) {
        viewData.concerts?.let {
            view?.show(items = viewData.concerts)
        }

        viewData.errorMessage?.let {
            view?.showError(
                messageId =  viewData.errorMessage
            )
        }

        if (viewData.isLoading) {
            view?.showProgress()
        } else {
            view?.hideProgress()
        }
    }
}