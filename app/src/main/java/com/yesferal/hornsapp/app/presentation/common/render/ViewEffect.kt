package com.yesferal.hornsapp.app.presentation.common.render

import androidx.annotation.StringRes

sealed class ViewEffect {
    class Toast(@StringRes val messageId: Int) : ViewEffect()
}
