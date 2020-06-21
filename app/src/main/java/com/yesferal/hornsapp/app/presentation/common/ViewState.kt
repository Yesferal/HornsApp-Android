package com.yesferal.hornsapp.app.presentation.common

open class ViewData

sealed class State<VD: ViewData> {

    data class Success<VD: ViewData>(
        val viewData: VD
    ): State<VD>()

    object Progress: State<Nothing>()

    data class Error(
        val message: Int
    ): State<Nothing>()
}