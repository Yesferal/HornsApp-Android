package com.yesferal.hornsapp.app.presentation.ui.filters

import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey

class CategoryViewData(
    val categoryKey: CategoryKey,
    name: String?,
    val isSelected: Boolean = false
): ViewData(categoryKey.toString(), name)