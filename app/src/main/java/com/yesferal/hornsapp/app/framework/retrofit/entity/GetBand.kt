/* Copyright © 2023 HornsApp. All rights reserved. */
package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.core.domain.entity.Band
import com.yesferal.hornsapp.core.domain.entity.drawer.TextDrawer

data class GetBand(
    val _id: String,
    val name: String?,
    val about: TextDrawer?,
    val membersImage: String?,
    val logoImage: String?,
    val countryTextDrawer: TextDrawer?,
    val genre: String?
) {
    fun mapToBand(): Band {
        return Band(
            id = this._id,
            name = this.name,
            description = this.about?.text,
            membersImage = this.membersImage,
            logoImage = this.logoImage,
            country = this.countryTextDrawer?.text,
            genre = this.genre
        )
    }
}