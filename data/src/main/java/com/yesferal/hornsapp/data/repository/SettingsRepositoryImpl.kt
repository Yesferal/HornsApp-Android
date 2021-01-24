package com.yesferal.hornsapp.data.repository

import com.yesferal.hornsapp.data.abstraction.PreferencesDataSource
import com.yesferal.hornsapp.domain.abstraction.SettingsRepository

class SettingsRepositoryImpl(
    private val preferencesDataSource: PreferencesDataSource
) : SettingsRepository {

    override fun getEnvironments()
        = preferencesDataSource.getEnvironments()

    override fun getEnvironment() = preferencesDataSource.getEnvironment()

    override fun updateDefaultEnvironment(environment: Int) {
        preferencesDataSource.updateDefaultEnvironment(environment)
    }
}