package com.yesferal.hornsapp.app.presentation.ui.favorite

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ConcertViewData

data class FavoritesViewState(
    val concerts: List<ConcertViewData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()