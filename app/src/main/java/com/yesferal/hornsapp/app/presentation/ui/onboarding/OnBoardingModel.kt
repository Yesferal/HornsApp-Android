package com.yesferal.hornsapp.app.presentation.ui.onboarding

data class OnBoardingViewState(
    val onBoardingViewData: OnBoardingViewData? = null,
    val isLoading: Boolean = false
)

data class OnBoardingViewData(
    val metalConcerts: Int,
    val rockConcerts: Int,
    val upcomingConcerts: Int,
    val total: Int
)