package com.yesferal.hornsapp.domain.abstraction

import com.yesferal.hornsapp.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.domain.entity.drawer.ScreenDrawer
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val screenDelay: Long
    fun getEnvironments(): List<Pair<String, String>>
    fun getDefaultEnvironment(): Int
    fun updateDefaultEnvironment(environment: Int)
    fun onBoardingIsVisible() : Boolean
    fun hideOnBoarding()
    fun getHomeDrawer(): Flow<List<ScreenDrawer>>
    fun getNewestDrawer(): Flow<List<ScreenDrawer>>
    fun getCategoryDrawer(): Flow<List<CategoryDrawer>>
}
