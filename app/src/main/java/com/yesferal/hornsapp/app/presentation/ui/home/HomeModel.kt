package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.annotation.StringRes
import com.yesferal.hornsapp.domain.entity.Concert

data class HomeViewState(
    val fragmentTitles: List<String>? = null,
    val concerts: List<Concert>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val allowRetry: Boolean = false
)