package com.yesferal.hornsapp.app.presentation.common

import androidx.annotation.StringRes

sealed class ViewEffect {
    class Toast(
        @StringRes val errorMessageId: Int
    ): ViewEffect()
}