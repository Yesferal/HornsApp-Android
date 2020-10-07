package com.yesferal.hornsapp.app.presentation.concert

import androidx.annotation.StringRes
import com.google.android.gms.ads.AdView
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.domain.entity.Concert

data class ConcertsViewData(
    val concerts: List<Concert>? = null,
    val categories: List<Category>? = null,
    val selectedCategory: Category? = null,
    val adView: AdView? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
    val allowRetry: Boolean = false
) : ViewData()