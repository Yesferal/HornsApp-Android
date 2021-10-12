package com.yesferal.hornsapp.app.framework.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yesferal.hornsapp.core.domain.common.HaDate
import com.yesferal.hornsapp.core.domain.entity.Concert

@Entity
data class RoomConcert(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String?,
    @ColumnInfo val headlinerImage: String?,
    @ColumnInfo val timeInMillis: Long?,
    @ColumnInfo val genre: String?,
) {
    fun mapTo() = Concert(
        id = id,
        name = name,
        headlinerImage = headlinerImage,
        dateTime = HaDate(timeInMillis),
        genre = genre,
        tags = null,
        isFavorite = true
    )
}