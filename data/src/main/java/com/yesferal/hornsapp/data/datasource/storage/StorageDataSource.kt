package com.yesferal.hornsapp.data.datasource.storage

interface StorageDataSource {
    fun setString(concerts: String)
    fun getString(): String
}