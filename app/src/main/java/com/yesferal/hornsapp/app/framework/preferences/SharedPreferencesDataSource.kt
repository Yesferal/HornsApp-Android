package com.yesferal.hornsapp.app.framework.preferences

import android.content.Context
import com.yesferal.hornsapp.data.abstraction.PreferencesDataSource

class SharedPreferencesDataSource(
    context: Context,
    name: String
) : PreferencesDataSource{
    enum class Key{
        ONBOARDING
    }

    private val sharedPreferences by lazy {
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    // TODO("Implement onboarding validation")
}