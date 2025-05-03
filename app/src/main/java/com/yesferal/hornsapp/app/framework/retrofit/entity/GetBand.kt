/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Band
import com.yesferal.hornsapp.core.domain.entity.util.LocalizedString

data class GetBand(
    val _id: String,
    val name: String?,
    val about: LocalizedString?,
    val images: BandImages?,
    val country: LocalizedString?,
    val formerIn: Int?,
    val genres: List<String>?,
) {
    fun mapToBand(): Band {
        return Band(
            id = this._id,
            name = this.name,
            membersImage = this.images?.members,
            logoImage = this.images?.logo,
            about = this.about?.text,
            country = this.country?.text,
            formerIn = this.formerIn,
            genres = this.genres
        )
    }
}

data class BandImages(
    val logo: String?,
    val members: String?,
)
