package com.yesferal.hornsapp.domain.entity

import java.net.URI

data class Concert (
    val id: String,
    val name: String?,
    val description: String?,
    val posterImage: String?,
    val headlinerImage: String?,
    val datetime: String?,
    val trailerUrl: URI?,
    val facebookUrl: URI?,
    var isFavorite: Boolean,
    // Details
    val state: String? = null,
    val local: Local? = null,
    val bands: List<Band>? = null
)