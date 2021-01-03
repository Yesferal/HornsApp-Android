package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.R
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

class NewestViewModel(
    getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : ViewModel() {
    private val _state = MutableLiveData<NewestViewState>()

    val state: LiveData<NewestViewState>
        get() = _state

    init {
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

                _state.value = NewestViewState(views)
            },
            onError = {
                _state.value = NewestViewState(
                    errorMessage = R.string.error_no_items,
                )
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

class NewestViewModelFactory(
    private val getConcertsByCategoryUseCase: GetConcertsByCategoryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsByCategoryUseCase::class.java
        ).newInstance(getConcertsByCategoryUseCase)
    }
}