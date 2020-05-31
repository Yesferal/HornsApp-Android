package com.yesferal.hornsapp.data.abstraction

interface StorageDataSource {
    fun setString(concerts: String)
    fun getString(): String?
}