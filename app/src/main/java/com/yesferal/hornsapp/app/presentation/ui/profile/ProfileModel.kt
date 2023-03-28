package com.yesferal.hornsapp.app.presentation.ui.profile

import com.yesferal.hornsapp.core.domain.navigator.NavViewData

class MessageViewData(
    val message: String?
): NavViewData {
    override val id: String
        get() = message.orEmpty()
    override val name: String?
        get() = message
}