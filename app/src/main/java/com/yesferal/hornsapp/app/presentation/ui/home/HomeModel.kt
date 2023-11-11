package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.annotation.StringRes
import com.yesferal.hornsapp.core.domain.entity.drawer.ViewDrawer

data class HomeViewState(
    val screens: List<Pair<ViewDrawer.Type, String>>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val allowRetry: Boolean = false
)
