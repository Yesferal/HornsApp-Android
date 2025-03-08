/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Venue
import com.yesferal.hornsapp.core.domain.entity.util.LocalizedString

data class GetVenue (
    val _id: String,
    val name: LocalizedString?,
    val description: LocalizedString?,
    val mapSearchName: String?,
    val latitude: String?,
    val longitude: String?,
    val imageUrl: String?,
) {
    fun mapToVenue(): Venue {
        return Venue(
            this._id,
            this.name?.text,
            this.description?.text,
            this.mapSearchName,
            this.latitude,
            this.longitude,
            this.imageUrl
        )
    }
}
