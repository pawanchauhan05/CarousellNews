package com.app.carousellnews.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Carousell(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "banner_url") val banner_url: String,
    @ColumnInfo(name = "time_created") val time_created: Long,
    @ColumnInfo(name = "rank") val rank: Int,
)