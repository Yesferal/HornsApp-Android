package com.yesferal.hornsapp.app.presentation.common.base

interface RenderState<VIEW_STATE> {
    fun render(viewState: VIEW_STATE)
}