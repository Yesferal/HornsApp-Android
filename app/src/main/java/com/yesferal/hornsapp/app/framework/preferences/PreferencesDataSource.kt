package com.yesferal.hornsapp.app.framework.preferences

import android.content.Context
import com.yesferal.hornsapp.app.framework.file.FileReaderManager
import com.yesferal.hornsapp.app.framework.gson.GsonDataSource
import com.yesferal.hornsapp.app.framework.logger.ChainLoggerProvider
import com.yesferal.hornsapp.app.framework.packageinfo.PackageInfoDataSource
import com.yesferal.hornsapp.app.framework.retrofit.ApiConstants
import com.yesferal.hornsapp.core.data.abstraction.storage.DrawerStorageDataSource
import com.yesferal.hornsapp.core.data.abstraction.storage.EnvironmentDataSource
import com.yesferal.hornsapp.core.data.abstraction.storage.OnBoardingDataSource
import com.yesferal.hornsapp.core.domain.entity.drawer.AppDrawer

class PreferencesDataSource(
    context: Context,
    name: String,
    private val apiConstants: ApiConstants,
    private val gsonDataSource: GsonDataSource,
    private val fileReaderManager: FileReaderManager,
    private val packageInfoDataSource: PackageInfoDataSource
) : EnvironmentDataSource, OnBoardingDataSource, DrawerStorageDataSource {

    enum class Key {
        ENVIRONMENT,
        ONBOARDING_VISIBILITY,
        APP_DRAWER
    }

    private val sharedPreferences by lazy {
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    override fun getDefaultEnvironment(): Int {
        val key = Key.ENVIRONMENT.name
        return sharedPreferences.getInt(key, 0)
    }

    override fun updateDefaultEnvironment(environment: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(Key.ENVIRONMENT.name, environment)
        editor.apply()
    }

    override fun getEnvironments() = apiConstants.environments

    override fun onBoardingIsVisible(): Boolean {
        return sharedPreferences.getBoolean(Key.ONBOARDING_VISIBILITY.name, true)
    }

    override fun hideOnBoarding() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(Key.ONBOARDING_VISIBILITY.name, false)
        editor.apply()
    }

    override fun getAppDrawer(): AppDrawer? {
        val localStorageAppDrawer = gsonDataSource.fromJsonSafe(getAppDrawerAsString(), AppDrawer::class.java)

        // TODO: Fix this, should use null to validate instead of 0
        val locaStorageAppDrawerAppVersion = localStorageAppDrawer?.appVersion ?: 0
        ChainLoggerProvider.provideLogger().d("appDrawer.appVersion: ${locaStorageAppDrawerAppVersion}")
        ChainLoggerProvider.provideLogger().d("packageInfoDataSource.getVersionCode(): ${packageInfoDataSource.getVersionCode()}")

        // Remove Local Storage AppDrawer data if it belong to a different version
        // This validate if user has a wrong AppDrawer version stored in shared preferences
        if (locaStorageAppDrawerAppVersion < packageInfoDataSource.getVersionCode()) {
            deleteAppDrawer()
            return gsonDataSource.fromJsonSafe(
                fileReaderManager.getJsonDataFromAsset("app_drawer.json"),
                AppDrawer::class.java
            )
        }

        return localStorageAppDrawer
    }

    private fun getAppDrawerAsString(): String? {
        return sharedPreferences.getString(Key.APP_DRAWER.name, null)
    }

    private fun deleteAppDrawer() {
        ChainLoggerProvider.provideLogger().d("Delete appDrawer.")
        val editor = sharedPreferences.edit()
        editor.putString(Key.APP_DRAWER.name, null)
        editor.apply()
    }

    override fun updateAppDrawer(appDrawer: AppDrawer) {
        val editor = sharedPreferences.edit()
        editor.putString(Key.APP_DRAWER.name, gsonDataSource.toJsonSafe(appDrawer))
        editor.apply()
    }
}
