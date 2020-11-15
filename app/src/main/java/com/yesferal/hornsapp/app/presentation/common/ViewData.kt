package com.yesferal.hornsapp.app.presentation.common

import com.yesferal.hornsapp.app.presentation.common.entity.ItemParcelable

open class ViewData(
    val id: String,
    val name: String?
) {
    fun asParcelable(): ItemParcelable {
        return ItemParcelable(
            id,
            name
        )
    }
}