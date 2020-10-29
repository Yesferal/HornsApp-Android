package com.yesferal.hornsapp.domain.entity

import java.net.URI
import java.util.*

data class Concert (
    val id: String,
    val name: String?,
    val description: String?,
    val headlinerImage: String?,
    val date: Date?,
    val dateTime: String?,
    val day: String?,
    val month: String?,
    val time: String?,
    val trailerUrl: URI?,
    val facebookUrl: URI?,
    var isFavorite: Boolean,
    val genre: String?,
    val tags: List<String>?,
    val ticketingHost: String? = null,
    val ticketingUrl: URI? = null,
    // Details
    val subGenres: String? = null,
    val state: String? = null,
    val venue: Venue? = null,
    val bands: List<Band>? = null
)