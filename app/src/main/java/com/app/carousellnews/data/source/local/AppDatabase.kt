package com.app.carousellnews.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.carousellnews.pojo.Carousell

@Database(entities = [Carousell::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}