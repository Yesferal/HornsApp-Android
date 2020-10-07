package com.yesferal.hornsapp.app.presentation.band

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.Band

class BandViewData(
    val band: Band? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessageId: Int? = null
) : ViewData()