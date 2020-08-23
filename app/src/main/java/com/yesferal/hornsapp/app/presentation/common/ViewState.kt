package com.yesferal.hornsapp.app.presentation.common

import androidx.annotation.StringRes

open class ViewData

sealed class ViewState<out VIEW_DATA: ViewData> {

    data class Success<VIEW_DATA: ViewData>(
        val viewData: VIEW_DATA
    ): ViewState<VIEW_DATA>()

    object Progress: ViewState<Nothing>()

    data class Error(
        @StringRes val message: Int
    ): ViewState<Nothing>()
}