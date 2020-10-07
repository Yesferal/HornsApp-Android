package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.domain.entity.Band

data class GetBand(
    val _id: String,
    val name: String?,
    val description: String?,
    val logoImage: String?,
    val membersImage: String?,
    val genre: String?,
    val country: String?
)

fun GetBand.mapToBand(): Band {
    return Band(
        _id = this._id,
        name = this.name,
        description = this.description,
        membersImage = this.membersImage,
        logoImage = this.logoImage,
        country = this.country,
        genre = this.genre
    )
}