package com.yesferal.hornsapp.app.presentation.ui.onboarding

import androidx.annotation.StringRes

data class OnBoardingViewState(
    val onBoardingViewData: OnBoardingViewData? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

data class OnBoardingViewData(
    val metalConcerts: Int,
    val rockConcerts: Int,
    val upcomingConcerts: Int,
    val total: Int
)