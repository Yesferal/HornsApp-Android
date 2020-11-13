package com.yesferal.hornsapp.app.presentation.ui.concert.search.adapter

import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.domain.entity.CategoryKey

class CategoryViewData(
    val categoryKey: CategoryKey,
    name: String?
): ViewData(categoryKey.toString(), name)