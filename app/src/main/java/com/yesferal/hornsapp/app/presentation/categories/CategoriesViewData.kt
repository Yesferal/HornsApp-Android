package com.yesferal.hornsapp.app.presentation.categories

import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.Category

data class CategoriesViewData(
    val categories: List<Category>
) : ViewData()