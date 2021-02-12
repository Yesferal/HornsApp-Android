package com.yesferal.hornsapp.app.framework.preferences

import android.content.Context
import com.yesferal.hornsapp.app.framework.retrofit.ApiConstants
import com.yesferal.hornsapp.data.abstraction.PreferencesDataSource

class SharedPreferencesDataSource(
    context: Context,
    name: String,
    private val apiConstants: ApiConstants
) : PreferencesDataSource {
    enum class Key{
        ENVIRONMENT,
        ONBOARDING_VISIBILITY
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
        return sharedPreferences.getBoolean(Key.ONBOARDING_VISIBILITY.toString(),true)
    }

    override fun hideOnBoarding() {
        val editor = sharedPreferences.edit()
        editor.putBoolean(Key.ONBOARDING_VISIBILITY.toString(), false)
        editor.apply()
    }
}