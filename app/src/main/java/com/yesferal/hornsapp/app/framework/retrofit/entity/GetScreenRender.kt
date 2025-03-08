/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.render.ScreenRender
import com.yesferal.hornsapp.core.domain.entity.render.ViewRender

data class GetScreenRender(
    private val views: List<ViewRender>?
) {
    fun mapToScreenRender(): ScreenRender {
        return ScreenRender(views)
    }
}
