package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.entity.Concert

class UpcomingViewState(
    val concerts: List<Concert>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()