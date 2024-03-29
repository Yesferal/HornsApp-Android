package com.yesferal.hornsapp.app.presentation.ui.band

import androidx.annotation.StringRes
import com.yesferal.hornsapp.core.domain.entity.Band

data class BandViewState(
    val band: Band? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
)