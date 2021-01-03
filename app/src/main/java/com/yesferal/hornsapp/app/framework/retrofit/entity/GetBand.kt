package com.yesferal.hornsapp.app.framework.retrofit.entity

import com.yesferal.hornsapp.domain.entity.Band

data class GetBand(
    val _id: String,
    val name: String?,
    val description: String?,
    val membersImage: String?,
    val logoImage: String?,
    val country: String?,
    val genre: String?
) {
    fun mapToBand(): Band {
        return Band(
            id = this._id,
            name = this.name,
            description = this.description,
            membersImage = this.membersImage,
            logoImage = this.logoImage,
            country = this.country,
            genre = this.genre
        )
    }
}