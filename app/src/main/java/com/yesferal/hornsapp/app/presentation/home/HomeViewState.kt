package com.yesferal.hornsapp.app.presentation.home

import androidx.annotation.StringRes
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.presentation.common.ViewState
import com.yesferal.hornsapp.domain.entity.Category

class HomeViewState(
    val categories: List<Category>? = null,
    val adView: AdView? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val allowRetry: Boolean = false
) : ViewState()