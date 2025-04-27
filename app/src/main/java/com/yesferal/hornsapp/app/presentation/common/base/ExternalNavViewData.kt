/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.presentation.common.base

import com.yesferal.hornsapp.app.framework.navigator.FragmentNavigator
import com.yesferal.hornsapp.core.domain.entity.render.NavigatorRender
import com.yesferal.hornsapp.core.domain.navigator.NavViewData

class ExternalNavViewData(
    private val uri: String
) : NavViewData {

    override fun toMap(): NavigatorRender {
        return NavigatorRender().apply {
            put(FragmentNavigator.PARAM_ANDROID_URI, uri)
        }
    }
}
