package com.yesferal.hornsapp.domain.abstraction

interface SettingsRepository {
    fun getEnvironments(): List<Pair<String, String>>
    fun getDefaultEnvironment(): Int
    fun updateDefaultEnvironment(environment: Int)
}