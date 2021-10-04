package com.yesferal.hornsapp.app.presentation.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.core.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.core.domain.common.Result
import com.yesferal.hornsapp.core.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.core.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.core.domain.usecase.UpdateVisibilityOnBoardingUseCase
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.delegate.DividerDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnBoardingViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val updateVisibilityOnBoardingUseCase: UpdateVisibilityOnBoardingUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _state = MutableLiveData<OnBoardingViewState>()

    val state: LiveData<OnBoardingViewState>
        get() = _state

    init {
        viewModelScope.launch {
            settingsRepository.getCategoryDrawer().collect {
                onRender(it)
            }
        }
    }

    private fun onRender(categoryDrawer: List<CategoryDrawer>) {
        viewModelScope.launch {
            val state = withContext(Dispatchers.IO) {
                when (val result = getConcertsUseCase()) {
                    is Result.Success -> {
                        val concerts = result.value
                        val delegates = mutableListOf<Delegate>()
                        val categoryDelegates = categoryDrawer.map { drawer ->
                            val amount = concerts.filter {
                                it.tags?.contains(drawer.key) == true
                            }.size
                            OnBoardingCategoryViewData(drawer.title?.text.orEmpty(), amount)
                        }
                        delegates.add(DividerDelegate(width = 24))
                        delegates.addAll(categoryDelegates)
                        delegates.add(DividerDelegate(width = 24))

                        OnBoardingViewState(categories = delegates.toList())
                    }
                    is Result.Error -> {
                        OnBoardingViewState()
                    }
                }
            }
            _state.value = state
        }
    }

    fun updateVisibilityOnBoarding() {
        updateVisibilityOnBoardingUseCase()
    }
}

class OnBoardingViewModelFactory(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val updateVisibilityOnBoardingUseCase: UpdateVisibilityOnBoardingUseCase,
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            UpdateVisibilityOnBoardingUseCase::class.java,
            SettingsRepository::class.java
        ).newInstance(getConcertsUseCase, updateVisibilityOnBoardingUseCase, settingsRepository)
    }
}
