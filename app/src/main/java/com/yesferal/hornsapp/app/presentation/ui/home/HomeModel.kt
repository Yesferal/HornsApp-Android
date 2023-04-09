package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.annotation.StringRes
import com.yesferal.hornsapp.core.domain.entity.drawer.ScreenDrawer

data class HomeViewState(
    val screens: List<Pair<ScreenDrawer.Type, String>>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val allowRetry: Boolean = false
)
