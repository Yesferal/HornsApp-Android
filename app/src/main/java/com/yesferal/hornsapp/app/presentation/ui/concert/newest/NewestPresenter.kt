package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.TitleViewData
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.ui.concert.search.mapToConcertViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase
import java.util.*

class NewestPresenter(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : BasePresenter<NewestFragment>() {

    fun onViewCreated()  {
        getConcertsByCategoryUseCase(
            categoryKey = CategoryKey.ALL.toString(),
            onSuccess = {
                val views = mutableListOf<ViewData>()
                val concertReversed = it.reversed()
                views.add(concertReversed.first().mapToConcertViewData())

                val thisYear = Calendar.getInstance().get(Calendar.YEAR)
                views.insertElementByYear(concertReversed, thisYear)

                val nextYear = thisYear + 1
                views.insertElementByYear(concertReversed, nextYear)

                val viewState = NewestViewState(views)

                view?.render(viewState)
            },
            onError = {
                val viewState = NewestViewState(
                    errorMessage = R.string.error_no_items,
                )

                view?.render(viewState)
            }
        )
    }

    private fun MutableList<ViewData>.insertElementByYear(
        concerts: List<Concert>,
        year: Int
    ) {
        this.add(TitleViewData(year.toString(), "#$year"))
        this.addAll(concerts
            .filter { year == it.year }
            .take(3)
            .map { concert ->
                concert.mapToUpcomingView()
            }
        )
    }
}