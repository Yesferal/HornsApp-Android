/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Venue
import com.yesferal.hornsapp.core.domain.entity.drawer.LocalizedString

data class GetVenue (
    val _id: String,
    val name: LocalizedString?,
    val mapSearchName: String?,
    val latitude: String?,
    val longitude: String?
) {
    fun mapToVenue(): Venue {
        return Venue(
            this._id,
            this.name?.text,
            this.mapSearchName,
            this.latitude,
            this.longitude
        )
    }
}
