package com.yesferal.hornsapp.app.presentation.common.adapter

import com.yesferal.hornsapp.domain.entity.Band

data class Item (
    val id: String,
    val name: String?,
    val imageUrl: String?
)

fun Band.mapToBaseItem() =
    Item(_id, name, membersImage)