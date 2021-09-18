package com.yesferal.hornsapp.data.abstraction.storage

interface OnBoardingDataSource {
    fun onBoardingIsVisible() : Boolean
    fun hideOnBoarding()
}
