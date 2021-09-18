package com.yesferal.hornsapp.data.abstraction.storage

interface EnvironmentDataSource {
    fun getDefaultEnvironment(): Int
    fun updateDefaultEnvironment(environment: Int)
    fun getEnvironments(): List<Pair<String, String>>
}
