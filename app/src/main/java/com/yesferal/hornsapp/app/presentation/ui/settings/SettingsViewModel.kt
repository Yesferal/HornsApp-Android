package com.yesferal.hornsapp.app.presentation.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yesferal.hornsapp.core.domain.usecase.GetDefaultEnvironmentUseCase
import com.yesferal.hornsapp.core.domain.usecase.GetSettingsUseCase
import com.yesferal.hornsapp.core.domain.usecase.UpdateSettingsUseCase

class SettingsViewModel(
    getDefaultEnvironmentUseCase: GetDefaultEnvironmentUseCase,
    getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
) : ViewModel() {
    private val _state = MutableLiveData<SettingsState>()

    val state: LiveData<SettingsState>
        get() = _state

    init {
        _state.value = SettingsState(
            environment = getDefaultEnvironmentUseCase(),
            environments = getSettingsUseCase()
        )
    }

    fun onSpinnerItemSelected(environment: Int) {
        updateSettingsUseCase.invoke(environment)
    }
}

class SettingsViewModelFactory(
    private val getDefaultEnvironmentUseCase: GetDefaultEnvironmentUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            GetDefaultEnvironmentUseCase::class.java,
            GetSettingsUseCase::class.java,
            UpdateSettingsUseCase::class.java
        ).newInstance(getDefaultEnvironmentUseCase, getSettingsUseCase, updateSettingsUseCase)
    }
}
