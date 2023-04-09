/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.ui.profile

import com.yesferal.hornsapp.app.framework.navigator.FragmentNavigator
import com.yesferal.hornsapp.core.domain.navigator.NavViewData
import com.yesferal.hornsapp.core.domain.navigator.Parameters

class MessageViewData(
    private val message: String?
): NavViewData {
    override fun toMap(): Parameters {
        return Parameters().apply {
            put(FragmentNavigator.PARAM_MESSAGE, message.orEmpty())
        }
    }
}