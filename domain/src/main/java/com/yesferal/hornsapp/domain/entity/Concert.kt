package com.yesferal.hornsapp.domain.entity

import java.net.URI
import java.util.*

data class Concert (
    val id: String,
    val name: String?,
    val description: String?,
    val headlinerImage: String?,
    val dateTime: Date?,
    val genre: String?,
    val tags: List<String>?,
    val trailerUrl: URI? = null,
    val facebookUrl: URI? = null,
    val venue: Venue? = null,
    val bands: List<Band>? = null,
    val ticketingUrl: URI? = null,
    val ticketingHost: String? = null,
    var isFavorite: Boolean
)