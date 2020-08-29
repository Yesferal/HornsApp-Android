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
    val category: String,
    // Details
    val genres: String? = null,
    val state: String? = null,
    val local: Local? = null,
    val bands: List<Band>? = null,
    val ticketingHost: String? = null,
    val ticketingUrl: URI? = null
)