package com.yesferal.hornsapp.app.presentation.common.render

import com.yesferal.hornsapp.app.presentation.common.base.BaseFragment

abstract class RenderFragment<VIEW_STATE> : BaseFragment(), RenderState<VIEW_STATE>, RenderEffect {

    override fun render(effect: ViewEffect) {
        when (effect) {
            is ViewEffect.Toast -> {
                showToast(effect.messageId)
            }
        }
    }
}
