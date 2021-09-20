package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.data.abstraction.remote.DrawerRemoteDataSource
import com.yesferal.hornsapp.data.abstraction.storage.EnvironmentDataSource
import com.yesferal.hornsapp.data.abstraction.storage.OnBoardingDataSource
import com.yesferal.hornsapp.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.domain.entity.drawer.CategoryDrawer
import com.yesferal.hornsapp.domain.entity.drawer.ScreenDrawer
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val environmentDataSource: EnvironmentDataSource,
    private val onBoardingDataSource: OnBoardingDataSource,
    private val drawerRemoteDataSource: DrawerRemoteDataSource
) : SettingsRepository {
    override val screenDelay: Long
        get() = 300

    override fun getEnvironments() = environmentDataSource.getEnvironments()

    override fun getDefaultEnvironment() = environmentDataSource.getDefaultEnvironment()

    override fun updateDefaultEnvironment(environment: Int) {
        environmentDataSource.updateDefaultEnvironment(environment)
    }

    override fun onBoardingIsVisible(): Boolean {
        return onBoardingDataSource.onBoardingIsVisible()
    }

    override fun hideOnBoarding() {
        return onBoardingDataSource.hideOnBoarding()
    }

    override fun getHomeDrawer(): Flow<List<ScreenDrawer>> {
        return drawerRemoteDataSource.homeDrawer
    }

    override fun getNewestDrawer(): Flow<List<ScreenDrawer>> {
        return drawerRemoteDataSource.newestDrawer
    }

    override fun getCategoryDrawer(): Flow<List<CategoryDrawer>> {
        return drawerRemoteDataSource.categoryDrawer
    }
}
