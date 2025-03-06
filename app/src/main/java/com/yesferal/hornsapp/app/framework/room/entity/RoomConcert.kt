package com.yesferal.hornsapp.app.framework.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yesferal.hornsapp.core.domain.entity.Concert

@Entity
data class RoomConcert(
    @PrimaryKey val id: String,
    @ColumnInfo val name: String?,
    @ColumnInfo val headlinerName: String?,
    @ColumnInfo val headlinerImageUrl: String?,
    @ColumnInfo val timeInMillis: Long?,
) {
    fun mapAsFavoriteConcert() = Concert.Builder(id)
        .addName(name)
        .addHeadlinerName(headlinerName)
        .addHeadlinerImageUrl(headlinerImageUrl)
        .addTimeInMillis(timeInMillis)
        .isFavorite(true)
        .build()
}
