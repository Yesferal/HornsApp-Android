package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.*
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData

data class UpcomingViewState(
    val categories: FiltersViewData? = null,
    val items: List<ViewData>? = null,
    val concerts: List<UpcomingViewData>? = null,
    val isLoading: Boolean = false,
    val error: ErrorViewData? = null
) : ViewState()

class UpcomingViewData(
    id: String,
    name: String?,
    val image: String?,
    val day: String?,
    val month: String?,
    val year: String?,
    val time: String?,
    val genre: String?
) : ViewData(id, name)

data class FiltersViewData(
    val categories: List<CategoryViewData>
) : ViewData(String(), String())

data class ErrorViewData(
    @DrawableRes val imageId: Int,
    @StringRes val errorMessage: Int
) : ViewData(imageId.toString(), errorMessage.toString())