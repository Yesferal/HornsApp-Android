package com.yesferal.hornsapp.app.presentation.common.render

import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment

abstract class RenderFragment<VIEW_STATE> : BaseFragment() {

    abstract fun render(viewState: VIEW_STATE)

    fun render(effect: ViewEffect) {
        when (effect) {
            is ViewEffect.Toast -> {
                showToast(effect.messageId)
            }
        }
    }
}
