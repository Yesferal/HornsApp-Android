package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.data.abstraction.remote.DrawerRemoteDataSource
import com.yesferal.hornsapp.data.abstraction.storage.DrawerStorageDataSource
import com.yesferal.hornsapp.data.abstraction.storage.EnvironmentDataSource
import com.yesferal.hornsapp.data.abstraction.storage.OnBoardingDataSource
import com.yesferal.hornsapp.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val environmentDataSource: EnvironmentDataSource,
    private val onBoardingDataSource: OnBoardingDataSource,
    private val drawerRemoteDataSource: DrawerRemoteDataSource,
    private val drawerStorageDataSource: DrawerStorageDataSource
) : SettingsRepository {

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

    override fun getAppDrawer(): Flow<AppDrawer> {
        return drawerRemoteDataSource.appDrawer
    }

    override fun updateDrawer(appDrawer: AppDrawer) {
        drawerStorageDataSource.updateAppDrawer(appDrawer)
    }
}
