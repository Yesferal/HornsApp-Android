package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.common.base.BasePresenter
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertsByCategoryUseCase
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted
import com.yesferal.hornsapp.multitype.model.ViewHolderBinding
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
                        day = firstConcert.dateTime?.dayFormatted(),
                        month = firstConcert.dateTime?.monthFormatted(),
                        year = firstConcert.dateTime?.yearFormatted(),
                        name = firstConcert.name,
                        time = firstConcert.dateTime?.timeFormatted(),
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
        val views = concerts
            .filter { year.toString() == it.dateTime?.yearFormatted() }
            .take(3)
            .sortedWith(compareBy { it.dateTime?.time })
            .map { concert ->
                NewestViewData(
                    id = concert.id,
                    day = concert.dateTime?.dayFormatted(),
                    month = concert.dateTime?.monthFormatted(),
                    name = concert.name,
                    ticketingHostName = concert.ticketingHost
                )
            }

        if (views.isEmpty()) { return }

        this.add(TitleViewData("#$year"))
        this.addAll(views)
    }
}