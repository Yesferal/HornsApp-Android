package com.yesferal.hornsapp.app.presentation.item.adapter

import com.yesferal.hornsapp.domain.entity.Band
import com.yesferal.hornsapp.domain.entity.Category
import com.yesferal.hornsapp.domain.entity.Concert

data class Item (
    val id: String,
    val name: String?,
    val imageUrl: String?,
    var isFavorite: Boolean
)

fun Category.mapToBaseItem() =
    Item(_id, name, imageUrl, isFavorite = false)

fun Band.mapToBaseItem() =
    Item(_id, name, membersImage, isFavorite)

fun Concert.mapToItem() =
    Item(id, name, posterImage, isFavorite)