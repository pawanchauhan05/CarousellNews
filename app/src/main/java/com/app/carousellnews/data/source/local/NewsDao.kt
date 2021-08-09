package com.app.carousellnews.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.carousellnews.pojo.Carousell

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(carousell: Carousell)

    @Query("SELECT * FROM Carousell")
    fun getAllNews(): MutableList<Carousell>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNews(photoList : MutableList<Carousell>)

    @Query("DELETE FROM Carousell")
    fun deleteAll()
}