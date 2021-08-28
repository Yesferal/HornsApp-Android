package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.annotation.StringRes
import com.yesferal.hornsapp.domain.entity.Concert
import com.yesferal.hornsapp.domain.entity.drawer.ScreenDrawer

data class HomeViewState(
    val screens: List<Pair<ScreenDrawer.Type, String>>? = null,
    val concerts: List<Concert>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val allowRetry: Boolean = false
)
