package com.yesferal.hornsapp.app.framework.preferences

import android.content.Context
import com.google.gson.Gson
import com.yesferal.hornsapp.app.framework.file.FileReaderManager
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
    private val gson: Gson,
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

    override fun getAppDrawer(): AppDrawer {
        val appDrawer = gson.fromJson(getAppDrawerAsString(), AppDrawer::class.java)

        ChainLoggerProvider.provideLogger().d("appDrawer.version: ${appDrawer?.versionCode}")
        ChainLoggerProvider.provideLogger().d("packageInfoDataSource.getVersionCode(): ${packageInfoDataSource.getVersionCode()}")
        if (appDrawer?.versionCode != packageInfoDataSource.getVersionCode()) {
            deleteAppDrawer()
            return gson.fromJson(
                fileReaderManager.getJsonDataFromAsset("app_drawer.json"),
                AppDrawer::class.java
            )
        }

        return appDrawer
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
        editor.putString(Key.APP_DRAWER.name, gson.toJson(appDrawer))
        editor.apply()
    }
}
