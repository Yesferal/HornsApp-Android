package com.yesferal.hornsapp.app.presentation.ui.home

import androidx.annotation.StringRes
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.presentation.common.base.ViewState

class HomeViewState(
    val fragmentTitles: List<String>? = null,
    val adView: AdView? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val allowRetry: Boolean = false
) : ViewState()