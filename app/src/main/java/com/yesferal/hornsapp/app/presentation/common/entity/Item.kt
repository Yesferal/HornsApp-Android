package com.yesferal.hornsapp.app.presentation.common.entity

data class Item (
    val id: String,
    val name: String?,
    val imageUrl: String?
) {
    fun asParcelable(): ItemParcelable {
        return ItemParcelable(
            id,
            name
        )
    }
}