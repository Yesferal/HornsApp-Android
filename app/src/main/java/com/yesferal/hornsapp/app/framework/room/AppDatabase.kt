package com.yesferal.hornsapp.app.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yesferal.hornsapp.app.framework.room.entity.RoomConcert

@Database(entities = [RoomConcert::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun concertDao(): ConcertDao
}