package com.yesferal.hornsapp.domain.entity

import java.net.URI

data class Concert (
    val id: String,
    val name: String?,
    val description: String?,
    val posterImage: String?,
    val headlinerImage: String?,
    val day: String?,
    val month: String?,
    val time: String?,
    val trailerUrl: URI?,
    val facebookUrl: URI?,
    var isFavorite: Boolean,
    val state: String? = null,
    val local: Local? = null,
    val bands: List<Band>? = null
)