package com.yesferal.hornsapp.app.framework.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yesferal.hornsapp.core.domain.entity.Concert

@Entity
data class RoomConcert(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String?,
    @ColumnInfo val headlinerImage: String?,
    @ColumnInfo val timeInMillis: Long?,
    @ColumnInfo val genre: String?,
) {
    fun mapAsFavoriteConcert() = Concert.Builder(id)
        .addName(name)
        .addHeadlinerImage(headlinerImage)
        .addTimeInMillis(timeInMillis)
        .addGenre(genre)
        .isFavorite(true)
        .build()
}
