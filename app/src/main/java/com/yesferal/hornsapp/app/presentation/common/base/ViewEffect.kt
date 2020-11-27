package com.yesferal.hornsapp.app.presentation.common.base

import androidx.annotation.StringRes

sealed class ViewEffect {
    class Toast(
        @StringRes val errorMessageId: Int
    ): ViewEffect()
}