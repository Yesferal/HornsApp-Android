package com.yesferal.hornsapp.data.abstraction

interface PreferencesDataSource {
    fun getDefaultEnvironment(): Int
    fun updateDefaultEnvironment(environment: Int)
    fun getEnvironments(): List<Pair<String, String>>
}