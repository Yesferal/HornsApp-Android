package com.yesferal.hornsapp.app.presentation.ui.favorite

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.ui.BasePresenter
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ConcertViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewState
import com.yesferal.hornsapp.domain.usecase.GetFavoriteConcertsUseCase

class FavoritesPresenter(
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase
) : BasePresenter<FavoritesFragment>() {
    fun onViewCreated()  {
        getFavoriteConcertsUseCase(
            onSuccess = { concerts ->
                val viewState = FavoritesViewState(concerts = concerts.map {
                    ConcertViewData(
                        id = it.id,
                        image = it.headlinerImage,
                        day = it.day,
                        month = it.month,
                        year = it.year.toString(),
                        name = it.name,
                        time = it.time,
                        genre = it.genre
                    )
                })

                view?.render(viewState)
            },
            onError = {
                val viewState = FavoritesViewState(
                    errorMessage = R.string.error_no_favorite_yet,
                )

                view?.render(viewState)
            }
        )
    }
}