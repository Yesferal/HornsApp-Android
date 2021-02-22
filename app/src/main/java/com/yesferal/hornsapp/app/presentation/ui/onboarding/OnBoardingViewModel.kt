package com.yesferal.hornsapp.app.presentation.ui.onboarding

import androidx.lifecycle.*
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.CategoryKey
import com.yesferal.hornsapp.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.domain.usecase.UpdateVisibilityOnBoardingUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class OnBoardingViewModel(
    getConcertsUseCase: GetConcertsUseCase,
    private val updateVisibilityOnBoardingUseCase: UpdateVisibilityOnBoardingUseCase
) : ViewModel() {
    private val _state = MutableLiveData<OnBoardingViewState>()

    val state: LiveData<OnBoardingViewState>
        get() = _state

    init {
        viewModelScope.launch {
            _state.value = withContext(Dispatchers.IO) {
                when (val result = getConcertsUseCase()) {
                    is Result.Success -> {
                        val concerts = result.value
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

                        OnBoardingViewState(onBoardingViewData = onBoardingViewData)
                    }
                    is Result.Error -> {
                        OnBoardingViewState()
                    }
                }
            }
        }
    }

    fun updateVisibilityOnBoarding(){
        updateVisibilityOnBoardingUseCase()
    }
}

class OnBoardingViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val updateVisibilityOnBoardingUseCase: UpdateVisibilityOnBoardingUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            UpdateVisibilityOnBoardingUseCase::class.java
        ).newInstance(getConcertsUseCase, updateVisibilityOnBoardingUseCase)
    }
}