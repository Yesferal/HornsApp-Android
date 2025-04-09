package com.yesferal.hornsapp.app.presentation.common.delegate

import com.yesferal.hornsapp.app.R
import com.yesferal.hornsapp.app.presentation.ui.concert.upcoming.ErrorViewData
import com.yesferal.hornsapp.delegate.abstraction.Delegate

data class DelegateViewState(
    val delegates: List<Delegate>? = null,
    val renderAd: Boolean = true
) {
    companion object {
        fun showDelegateViewStateError(): DelegateViewState {
            return DelegateViewState(
                delegates = listOf(
                    ErrorViewData(
                        R.drawable.ic_music_note,
                        R.string.error_default
                    )
                )
            )
        }
    }
}
