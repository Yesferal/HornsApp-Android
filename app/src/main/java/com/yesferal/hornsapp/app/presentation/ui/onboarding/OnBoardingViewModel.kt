package com.yesferal.hornsapp.app.presentation.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import java.util.*

class OnBoardingViewModel(
    getConcertsUseCase: GetConcertsUseCase
) : ViewModel() {
    private val _state = MutableLiveData<OnBoardingViewState>()

    val state: LiveData<OnBoardingViewState>
        get() = _state

    init {
        getConcertsUseCase(
            onSuccess = { concerts ->
                val onBoardingViewData = OnBoardingViewData(
                    metalConcerts = concerts.filter {
                        it.tags?.contains(CategoryKey.METAL.toString()) == true
                    }.size,
                    rockConcerts = concerts.filter {
                        it.tags?.contains(CategoryKey.ROCK.toString()) == true
                    }.size,
                    upcomingConcerts = concerts.filter {
                        val dateTime = it.dateTime?: return@filter false

                        val twoMonthsInMillis = 5184000000

                        dateTime.before(Date(Calendar.getInstance().timeInMillis + (twoMonthsInMillis)))
                    }.size,
                    total = concerts.size
                )
                _state.value = OnBoardingViewState(onBoardingViewData = onBoardingViewData)
            },
            onError = {
                _state.value = OnBoardingViewState(
                    errorMessage = R.string.error_default
                )
            }
        )
    }
}

class OnBoardingViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java
        ).newInstance(getConcertsUseCase)
    }
}