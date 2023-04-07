package com.yesferal.hornsapp.app.presentation.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.core.domain.usecase.GetVisibilityOnBoardingUseCase

class SplashViewModel(
    private val getVisibilityOnBoardingUseCase: GetVisibilityOnBoardingUseCase
) : ViewModel() {
    private val _state = MutableLiveData<SplashState>()

    val state: LiveData<SplashState>
        get() = _state

    fun onViewCreated() {
        _state.value = SplashState(
            onBoardingVisibility = getVisibilityOnBoardingUseCase()
        )
    }
}

class SplashViewModelFactory(
    private val getVisibilityOnBoardingUseCase: GetVisibilityOnBoardingUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetVisibilityOnBoardingUseCase::class.java
        ).newInstance(getVisibilityOnBoardingUseCase)
    }
}
