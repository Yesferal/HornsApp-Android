/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Venue
import com.yesferal.hornsapp.core.domain.entity.drawer.LocalizedString

data class GetVenue (
    val _id: String,
    val name: String?,
    val shortName: LocalizedString?,
    val latitude: String?,
    val longitude: String?
) {
    fun mapToVenue(): Venue {
        return Venue(
            this._id,
            this.name,
            this.shortName?.text,
            this.latitude,
            this.longitude
        )
    }
}
