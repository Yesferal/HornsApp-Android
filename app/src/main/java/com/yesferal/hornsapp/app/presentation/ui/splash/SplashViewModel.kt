package com.yesferal.hornsapp.app.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.domain.usecase.*

class SplashViewModel(
    getVisibilityOnBoardingUseCase: GetVisibilityOnBoardingUseCase,
    private val updateVisibilityOnBoardingUseCase: UpdateVisibilityOnBoardingUseCase
) : ViewModel() {
    private val _state = MutableLiveData<SplashState>()

    val state: LiveData<SplashState>
        get() = _state

    init {
        _state.value = SplashState(
            onBoardingVisibility = getVisibilityOnBoardingUseCase()
        )
    }

    fun updateVisibilityOnBoarding(){
        updateVisibilityOnBoardingUseCase()
    }
}

class SplashViewModelFactory(
    private val getVisibilityOnBoardingUseCase: GetVisibilityOnBoardingUseCase,
    private val updateVisibilityOnBoardingUseCase: UpdateVisibilityOnBoardingUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetVisibilityOnBoardingUseCase::class.java,
            UpdateVisibilityOnBoardingUseCase::class.java
        ).newInstance(getVisibilityOnBoardingUseCase, updateVisibilityOnBoardingUseCase)
    }
}