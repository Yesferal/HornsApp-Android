package com.yesferal.hornsapp.app.framework.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yesferal.hornsapp.domain.entity.Concert
import java.util.*

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
        dateTime = Date().apply { time = timeInMillis?: 0 },
        genre = genre,
        tags = null,
        isFavorite = true
    )
}