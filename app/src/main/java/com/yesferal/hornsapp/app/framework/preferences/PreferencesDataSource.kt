package com.yesferal.hornsapp.app.framework.preferences

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yesferal.hornsapp.data.abstraction.StorageDataSource
import com.yesferal.hornsapp.domain.entity.Concert

enum class Key{
    SHARED_PREFERENCES,
    FAVORITE_CONCERTS,
    CONCERTS
}

class SharedPreferencesDataSource(
    context: Context
) : StorageDataSource {
    private val sharedPreferences by lazy {
        val key = Key.SHARED_PREFERENCES.toString()
        context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }

    override fun insertFavoriteConcert(
        concert: Concert,
        onComplete: () -> Unit
    ) {
        val favoriteConcerts = getFavoriteConcerts()?: mutableListOf()

        if (!favoriteConcerts.contains(concert.id)) {
            favoriteConcerts.add(concert.id)
        }

        setFavoriteConcerts(favoriteConcerts)
        onComplete()
    }

    override fun removeFavoriteConcert(
        concert: Concert,
        onComplete: () -> Unit
    ) {
        val favoriteConcerts = getFavoriteConcerts()?: mutableListOf()

        if (favoriteConcerts.contains(concert.id)) {
            favoriteConcerts.remove(concert.id)
        } else {
            return
        }

        if (favoriteConcerts.isEmpty()) {
            setFavoriteConcerts(null)
            onComplete()
        } else {
            setFavoriteConcerts(favoriteConcerts)
            onComplete()
        }
    }

    private fun setFavoriteConcerts(
        concerts: List<String>?
    ) {
        val editor = sharedPreferences.edit()
        editor.putStringSet(Key.FAVORITE_CONCERTS.toString(), concerts?.toMutableSet())
        editor.apply()
    }

    override fun getFavoriteConcerts(): MutableList<String>? {
        val key = Key.FAVORITE_CONCERTS.toString()
        return sharedPreferences.getStringSet(key, null)?.toMutableList()
    }

    override fun getConcerts(): List<Concert>? {
        val key = Key.CONCERTS.toString()
        val jsonOfConcerts = sharedPreferences.getString(key, null)
        val itemType = object : TypeToken<List<Concert>>() {}.type
        return Gson().fromJson<List<Concert>>(jsonOfConcerts, itemType)
    }

    override fun insertConcerts(concerts: List<Concert>?) {
        val jsonOfConcerts = Gson().toJson(concerts)
        val editor = sharedPreferences.edit()
        editor.putString(Key.CONCERTS.toString(), jsonOfConcerts)
        editor.apply()
    }
}