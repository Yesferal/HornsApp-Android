/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Band
import com.yesferal.hornsapp.core.domain.entity.drawer.LocalizedString

data class GetBand(
    val _id: String,
    val name: String?,
    val images: BandImages?,
    val about: LocalizedString?,
    val country: LocalizedString?,
    val formerIn: String?
) {
    fun mapToBand(): Band {
        return Band(
            id = this._id,
            name = this.name,
            membersImage = this.images?.members,
            logoImage = this.images?.logo,
            description = this.about?.text,
            country = this.country?.text,
            genre = this.formerIn
        )
    }
}

class BandImages(
    val logo: String?,
    val members: String?,
)
