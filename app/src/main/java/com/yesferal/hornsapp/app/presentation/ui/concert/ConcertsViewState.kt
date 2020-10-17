package com.yesferal.hornsapp.app.presentation.ui.concert

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.entity.Concert

data class ConcertsViewState(
    val concerts: List<Concert>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()