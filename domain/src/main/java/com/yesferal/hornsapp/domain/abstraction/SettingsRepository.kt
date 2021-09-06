package com.yesferal.hornsapp.domain.abstraction

import com.yesferal.hornsapp.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.domain.entity.drawer.ScreenDrawer
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getEnvironments(): List<Pair<String, String>>
    fun getDefaultEnvironment(): Int
    fun updateDefaultEnvironment(environment: Int)
    fun onBoardingIsVisible() : Boolean
    fun hideOnBoarding()
    fun getScreenDrawer(): Flow<List<ScreenDrawer>>
    fun getCategoryDrawer(): Flow<List<CategoryDrawer>>
}
