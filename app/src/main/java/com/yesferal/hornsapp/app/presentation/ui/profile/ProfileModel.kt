/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.profile

import com.yesferal.hornsapp.app.framework.navigator.FragmentNavigator
import com.yesferal.hornsapp.core.domain.entity.render.NavigatorRender
import com.yesferal.hornsapp.core.domain.navigator.NavViewData

class MessageViewData(
    private val message: String?
): NavViewData {
    override fun toMap(): NavigatorRender {
        return NavigatorRender().apply {
            put(FragmentNavigator.PARAM_MESSAGE, message.orEmpty())
        }
    }
}