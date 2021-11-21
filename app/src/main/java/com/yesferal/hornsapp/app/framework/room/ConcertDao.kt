package com.yesferal.hornsapp.app.framework.room

import androidx.room.*
import com.yesferal.hornsapp.app.framework.room.entity.RoomConcert

@Dao
interface ConcertDao {
    @Query("SELECT * FROM RoomConcert")
    fun getAll(): List<RoomConcert>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(roomConcert: RoomConcert)

    @Delete
    fun delete(roomConcert: RoomConcert)
}
