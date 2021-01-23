package com.yesferal.hornsapp.domain.abstraction

interface SettingsRepository {
    fun getEnvironments(): List<Pair<String, String>>
    fun getEnvironment(): Int
    fun updateDefaultEnvironment(environment: Int)
}