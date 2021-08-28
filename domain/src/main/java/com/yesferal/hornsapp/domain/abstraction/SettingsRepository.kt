package com.yesferal.hornsapp.domain.abstraction

import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer

interface SettingsRepository {
    fun getEnvironments(): List<Pair<String, String>>
    fun getDefaultEnvironment(): Int
    fun updateDefaultEnvironment(environment: Int)
    fun onBoardingIsVisible() : Boolean
    fun hideOnBoarding()
    fun getAppDrawer(): AppDrawer
}
