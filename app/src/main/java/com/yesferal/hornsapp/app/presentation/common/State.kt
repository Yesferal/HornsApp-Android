package com.yesferal.hornsapp.app.presentation.common

sealed class State<T> {
    // TODO: Replace T with ViewData
    // There will be problems with 2 params
    data class Success<T>(
        val data: T
    ): State<T>()

    object Progress: State<Nothing>()

    data class Error(
        val message: Int
    ): State<Nothing>()
}