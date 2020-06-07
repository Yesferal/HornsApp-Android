package com.yesferal.hornsapp.app.framework.preferences

import android.content.Context
import com.yesferal.hornsapp.data.abstraction.StorageDataSource

enum class Key(val value: String) {
    SHARED_PREFERENCES(SHARED_PREFERENCES.toString()),
    CONCERTS(CONCERTS.toString())
}

class SharedPreferencesDataSource(
    context: Context
) : StorageDataSource {
    private val sharedPreferences by lazy {
        val key = Key.SHARED_PREFERENCES.value
        context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    override fun setString(concerts: String) {
        val editor = sharedPreferences.edit()
        editor.putString(Key.CONCERTS.value, concerts)
        editor.apply()
    }

    override fun getString(): String? {
        val key = Key.CONCERTS.value
        return sharedPreferences.getString(key, null)
    }
}