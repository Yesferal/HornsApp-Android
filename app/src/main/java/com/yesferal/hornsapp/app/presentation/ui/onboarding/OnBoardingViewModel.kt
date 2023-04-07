package com.yesferal.hornsapp.app.presentation.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yesferal.hornsapp.core.domain.abstraction.DrawerRepository
import com.yesferal.hornsapp.core.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.core.domain.usecase.FilterConcertsByCategoryUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetConcertsUseCase
import com.yesferal.hornsapp.core.domain.usecase.UpdateVisibilityOnBoardingUseCase
import com.yesferal.hornsapp.core.domain.util.HaResult
import com.yesferal.hornsapp.delegate.abstraction.Delegate
import com.yesferal.hornsapp.delegate.delegate.DividerDelegate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnBoardingViewModel(
    private val getConcertsUseCase: GetConcertsUseCase,
    private val updateVisibilityOnBoardingUseCase: UpdateVisibilityOnBoardingUseCase,
    private val drawerRepository: DrawerRepository,
    private val filterConcertsByCategoryUseCase: FilterConcertsByCategoryUseCase
) : ViewModel() {
    private val _state = MutableLiveData<OnBoardingViewState>()

    val state: LiveData<OnBoardingViewState>
        get() = _state

    init {
        viewModelScope.launch {
            drawerRepository.getCategoryDrawer().collect {
                onRender(it)
            }
        }
    }

    private fun onRender(categoryDrawer: List<CategoryDrawer>) {
        viewModelScope.launch {
            val state = withContext(Dispatchers.IO) {
                when (val result = getConcertsUseCase()) {
                    is HaResult.Success -> {
                        val concerts = result.value
                        val categoryDelegates = categoryDrawer.map { drawer ->
                            val amount = filterConcertsByCategoryUseCase(concerts, drawer.key).size
                            OnBoardingCategoryViewData(drawer.title?.text.orEmpty(), amount)
                        }
                        val delegates = mutableListOf<Delegate>()
                        delegates.add(DividerDelegate(width = 24))
                        delegates.addAll(categoryDelegates)
                        delegates.add(DividerDelegate(width = 24))

                        OnBoardingViewState(categories = delegates.toList())
                    }
                    is HaResult.Error -> {
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
    private val drawerRepository: DrawerRepository,
    private val filterConcertsByCategoryUseCase: FilterConcertsByCategoryUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetConcertsUseCase::class.java,
            UpdateVisibilityOnBoardingUseCase::class.java,
            DrawerRepository::class.java,
            FilterConcertsByCategoryUseCase::class.java
        ).newInstance(getConcertsUseCase, updateVisibilityOnBoardingUseCase, drawerRepository, filterConcertsByCategoryUseCase)
    }
}
