package com.yesferal.hornsapp.data.abstraction.features

interface EnvironmentDataSource {
    fun getDefaultEnvironment(): Int
    fun updateDefaultEnvironment(environment: Int)
    fun getEnvironments(): List<Pair<String, String>>
}
