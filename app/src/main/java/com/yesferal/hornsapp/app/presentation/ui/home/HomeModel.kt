package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.framework.adMob.AdViewData

data class HomeViewState(
    val fragmentTitles: List<String>? = null,
    val adViewData: AdViewData? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val allowRetry: Boolean = false
)