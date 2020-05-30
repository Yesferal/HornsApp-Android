package com.yesferal.hornsapp.domain.entity

data class Concert (
    val id: String,
    val name: String?,
    val description: String?,
    val posterImage: String?,
    val headlinerImage: String?,
    val date: String?,
    val time: String?
)