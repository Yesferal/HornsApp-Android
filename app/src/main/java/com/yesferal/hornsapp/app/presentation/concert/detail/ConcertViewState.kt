package com.yesferal.hornsapp.app.presentation.concert.detail

import androidx.annotation.StringRes
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.entity.Concert

data class ConcertViewState(
    val concert: Concert? = null,
    val adView: AdView? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
) : ViewState()