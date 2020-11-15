package com.yesferal.hornsapp.app.presentation.ui.concert.upcoming

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.*
import com.yesferal.hornsapp.app.presentation.ui.filters.CategoryViewData

data class UpcomingViewState(
    val categories: FiltersViewData? = null,
    val concerts: List<ConcertViewData>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewState()

class ConcertViewData(
    id: String,
    name: String?,
    val image: String?,
    val day: String?,
    val month: String?,
    val year: String?,
    val time: String?,
    val genre: String?
): ViewData(id, name)

data class FiltersViewData(
    val categories: List<CategoryViewData>
): ViewData(String(), String())