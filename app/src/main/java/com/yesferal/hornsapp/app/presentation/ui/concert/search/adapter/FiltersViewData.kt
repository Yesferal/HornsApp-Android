package com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter

import com.yesferal.hornsapp.app.presentation.common.ViewData

data class FiltersViewData(
    val categories: List<CategoryViewData>
): ViewData(String(), String())