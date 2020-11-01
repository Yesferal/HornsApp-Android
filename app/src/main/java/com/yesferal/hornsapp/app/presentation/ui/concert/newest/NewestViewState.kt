package com.yesferal.hornsapp.app.presentation.ui.concert.newest

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.common.ViewState

class NewestViewState(
    val concerts: List<ViewData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()