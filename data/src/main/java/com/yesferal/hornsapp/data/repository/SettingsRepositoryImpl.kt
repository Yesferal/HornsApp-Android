package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.data.abstraction.ApiDataSource
import com.yesferal.hornsapp.data.abstraction.features.EnvironmentDataSource
import com.yesferal.hornsapp.data.abstraction.features.OnBoardingDataSource
import com.yesferal.hornsapp.data.abstraction.features.DrawerDataSource
import com.yesferal.hornsapp.data.abstraction.features.UpdateDrawerDataSource
import com.yesferal.hornsapp.domain.abstraction.Logger
import com.yesferal.hornsapp.domain.abstraction.SettingsRepository
import com.yesferal.hornsapp.domain.common.Result
import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer

class SettingsRepositoryImpl(
    private val environmentDataSource: EnvironmentDataSource,
    private val onBoardingDataSource: OnBoardingDataSource,
    private val drawerDataSource: DrawerDataSource,
    private val apiDataSource: ApiDataSource,
    private val updateDrawerDataSource: UpdateDrawerDataSource,
    private val logger: Logger
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

    override fun getAppDrawer(): AppDrawer? {
        return updateDrawerDataSource.getAppDrawer() ?: drawerDataSource.getAppDrawer()
    }

    override suspend fun syncAppDrawer() {
        apiDataSource.getAppDrawer().let {
            when (it) {
                is Result.Success -> {
                    updateDrawerDataSource.updateAppDrawer(it.value)
                }
                is Result.Error -> {
                    logger.e("App couldn't sync as expected.")
                }
            }
        }
    }
}
