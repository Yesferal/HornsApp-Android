package com.yesferal.hornsapp.app.presentation.common.adapter

import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Category

data class BaseItem (
    val id: String,
    val name: String?,
    val imageUrl: String?
)

fun Category.mapToBaseItem() =
    BaseItem(_id, name, imageUrl)

fun Band.mapToBaseItem() =
    BaseItem(_id, name, membersImage)