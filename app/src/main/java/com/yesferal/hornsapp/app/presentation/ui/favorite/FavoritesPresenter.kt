package com.yesferal.hornsapp.app.presentation.ui.favorite

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BasePresenter
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.domain.usecase.GetFavoriteConcertsUseCase
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted

class FavoritesPresenter(
    private val getFavoriteConcertsUseCase: GetFavoriteConcertsUseCase
) : BasePresenter<FavoritesFragment>() {
    fun onViewCreated()  {
        getFavoriteConcertsUseCase(
            onSuccess = { concerts ->
                val viewState = FavoritesViewState(items = concerts
                    .sortedWith(compareBy { it.dateTime?.time })
                    .map {
                        UpcomingViewData(
                            id = it.id,
                            image = it.headlinerImage,
                            day = it.dateTime?.dayFormatted(),
                            month = it.dateTime?.monthFormatted(),
                            year = it.dateTime?.yearFormatted(),
                            name = it.name,
                            time = it.dateTime?.timeFormatted(),
                            genre = it.genre
                        )
                    }
                )

                view?.render(viewState)
            },
            onError = {
                val viewState = FavoritesViewState(
                    items = listOf(
                        ErrorViewData(
                            R.drawable.ic_music_note,
                            R.string.error_no_favorite_yet
                        )
                    )
                )

                view?.render(viewState)
            }
        )
    }
}