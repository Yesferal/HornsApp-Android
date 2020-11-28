package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BasePresenter
import com.yesferal.hornsapp.app.presentation.common.multitype.ViewHolderBinding
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase
import java.util.*

class NewestPresenter(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : BasePresenter<NewestFragment>() {

    fun onViewCreated()  {
        getConcertsByCategoryUseCase(
            categoryKey = CategoryKey.ALL,
            onSuccess = {
                val views = mutableListOf<ViewHolderBinding>()
                val concertReversed = it.reversed()
                val firstConcert = concertReversed.first()
                views.add(
                    UpcomingViewData(
                        id = firstConcert.id,
                        image = firstConcert.headlinerImage,
                        day = firstConcert.day,
                        month = firstConcert.month,
                        year = firstConcert.year.toString(),
                        name = firstConcert.name,
                        time = firstConcert.time,
                        genre = firstConcert.genre
                    )
                )

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

    private fun MutableList<ViewHolderBinding>.insertElementByYear(
        concerts: List<Concert>,
        year: Int
    ) {
        this.add(TitleViewData(year.toString(), "#$year"))
        this.addAll(concerts
            .filter { year == it.year }
            .take(3)
            .map { concert ->
                NewestViewData(
                    id = concert.id,
                    day = concert.day,
                    month = concert.month,
                    name = concert.name,
                    ticketingHostName = concert.ticketingHost
                )
            }
        )
    }
}