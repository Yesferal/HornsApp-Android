package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.UpcomingViewData
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.util.dayFormatted
import com.yesferal.hornsapp.domain.util.monthFormatted
import com.yesferal.hornsapp.domain.util.timeFormatted
import com.yesferal.hornsapp.domain.util.yearFormatted
import com.yesferal.hornsapp.multitype.model.ViewHolderBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class NewestViewModel(
    private val getConcertsUseCase: GetConcertsUseCase
) : ViewModel() {

    private val _stateNewest = MutableLiveData<NewestViewState>()

    val stateNewest: LiveData<NewestViewState>
        get() = _stateNewest

    init {
        viewModelScope.launch {
            _stateNewest.value = getNewestConcerts()
        }
    }

    private suspend fun getNewestConcerts() = withContext(Dispatchers.IO) {
        when (val result = getConcertsUseCase()) {
            is Result.Success -> {
                val concerts = result.value

                if (concerts.isEmpty()) {
                    return@withContext NewestViewState(
                        errorMessage = R.string.error_no_items,
                    )
                }

                val views = mutableListOf<ViewHolderBinding>()
                val concertReversed = concerts.reversed()
                val firstConcert = concertReversed.toMutableList().removeFirst()
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

                return@withContext NewestViewState(views)
            }
            is Result.Error -> {
                return@withContext NewestViewState(
                    errorMessage = R.string.error_no_items,
                )
            }
        }
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

        if (views.isEmpty()) {
            return
        }

        this.add(TitleViewData("#$year"))
        this.addAll(views)
    }
}

class NewestViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java
        ).newInstance(getConcertsUseCase)
    }
}
