package com.yesferal.hornsapp.app.presentation.ui.filters

import com.yesferal.hornsapp.app.presentation.common.base.ViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey

data class CategoryViewData(
    val categoryKey: CategoryKey,
    val name: String?,
    val isSelected: Boolean = false
): ViewData