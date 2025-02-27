/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.drawer.ScreenRender
import com.yesferal.hornsapp.core.domain.entity.drawer.ViewDrawer

data class GetScreenRender(
    private val views: List<ViewDrawer>?
) {
    fun mapToScreenRender(): ScreenRender {
        return ScreenRender(views)
    }
}
