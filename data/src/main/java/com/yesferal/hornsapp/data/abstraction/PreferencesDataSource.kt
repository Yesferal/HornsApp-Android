package com.yesferal.hornsapp.data.abstraction

interface PreferencesDataSource {
    fun getEnvironment(): Int
    fun updateDefaultEnvironment(environment: Int)
    fun getEnvironments(): List<Pair<String, String>>
}