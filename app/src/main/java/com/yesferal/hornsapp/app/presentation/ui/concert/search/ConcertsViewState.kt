package com.yesferal.hornsapp.app.presentation.ui.concert.search

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter.FiltersViewData

data class ConcertsViewState(
    val categories: FiltersViewData? = null,
    val concerts: List<ConcertViewData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()