package com.yesferal.hornsapp.data.abstraction.features

interface OnBoardingDataSource {
    fun onBoardingIsVisible() : Boolean
    fun hideOnBoarding()
}
