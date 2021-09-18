package com.yesferal.hornsapp.app.presentation.common.render

interface RenderState<VIEW_STATE> {
    fun render(viewState: VIEW_STATE)
}
