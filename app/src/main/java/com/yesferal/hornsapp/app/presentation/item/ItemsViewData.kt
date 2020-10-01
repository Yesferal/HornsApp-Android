package com.yesferal.hornsapp.app.presentation.item

import androidx.annotation.StringRes
import com.yesferal.hornsapp.app.presentation.common.ViewData
import com.yesferal.hornsapp.app.presentation.item.adapter.Item

class ItemsViewData(
    val concerts: List<Item>? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
) : ViewData()