package com.yesferal.hornsapp.app.presentation.ui.filters

import com.yesferal.hornsapp.domain.entity.CategoryKey

data class CategoryViewData(
    val categoryKey: CategoryKey,
    val name: String?,
    val isSelected: Boolean = false
)