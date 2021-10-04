package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Venue

data class GetVenue (
    val _id: String,
    val name: String?,
    val latitude: String?,
    val longitude: String?
) {
    fun mapToVenue(): Venue {
        return Venue(
            this._id,
            this.name,
            this.latitude,
            this.longitude
        )
    }
}