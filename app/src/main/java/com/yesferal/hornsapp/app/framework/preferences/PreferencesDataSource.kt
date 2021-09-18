package com.yesferal.hornsapp.app.framework.preferences

import android.content.Context
import com.google.gson.Gson
import com.yesferal.hornsapp.app.framework.file.FileReaderManager
import com.yesferal.hornsapp.app.framework.retrofit.ApiConstants
import com.yesferal.hornsapp.data.abstraction.storage.DrawerStorageDataSource
import com.yesferal.hornsapp.data.abstraction.storage.EnvironmentDataSource
import com.yesferal.hornsapp.data.abstraction.storage.OnBoardingDataSource
import com.yesferal.hornsapp.domain.entity.drawer.AppDrawer

class PreferencesDataSource(
    context: Context,
    name: String,
    private val apiConstants: ApiConstants,
    private val gson: Gson,
    private val fileReaderManager: FileReaderManager
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
        val key = Key.ENVIRONMENT.toString()
        return sharedPreferences.getInt(key, 0)
    }

    override fun updateDefaultEnvironment(environment: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(Key.ENVIRONMENT.toString(), environment)
        editor.apply()
    }

    override fun getEnvironments() = apiConstants.environments

    override fun onBoardingIsVisible(): Boolean {
        return sharedPreferences.getBoolean(Key.ONBOARDING_VISIBILITY.toString(), true)
    }

    override fun hideOnBoarding() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(Key.ONBOARDING_VISIBILITY.toString(), false)
        editor.apply()
    }

    override fun getAppDrawer(): AppDrawer {
        return gson.fromJson(getAppDrawerAsString(), AppDrawer::class.java)
    }

    private fun getAppDrawerAsString(): String? {
        return sharedPreferences.getString(Key.APP_DRAWER.toString(), null)
            ?: fileReaderManager.getJsonDataFromAsset("app_drawer.json")
    }

    override fun updateAppDrawer(appDrawer: AppDrawer) {
        val editor = sharedPreferences.edit()
        editor.putString(Key.APP_DRAWER.toString(), gson.toJson(appDrawer))
        editor.apply()
    }
}
