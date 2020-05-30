package com.yesferal.hornsapp.app.presentation.common.base

sealed class State<T> {
    data class Success<T>(
        val data: T
    ): State<T>()

    object Progress: State<Nothing>()

    data class Error(
        val message: Int
    ): State<Nothing>()
}