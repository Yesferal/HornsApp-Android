package com.yesferal.hornsapp.app.presentation.common.base

interface RenderState<VIEW_STATE: ViewState> {
    fun render(viewState: VIEW_STATE)
}