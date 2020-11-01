package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.ViewState

data class ConcertsViewState(
    val concerts: List<ConcertViewData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()