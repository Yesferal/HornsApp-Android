package com.yesferal.hornsapp.data.repository.entity

import java.util.*

data class GetConcerts(
    val _id: String,
    val name: String?,
    val description: String?,
    val posterImage: String?,
    val headlinerImage: String?,
    val dateTime: Date?
)