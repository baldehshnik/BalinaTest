package com.sparkfusion.balina.test.data.entity.image

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class LocalImageEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("url")
    val url: String,

    @ColumnInfo("date")
    val date: Long,

    @ColumnInfo("lat")
    val lat: Double,

    @ColumnInfo("lng")
    val lng: Double,

    @ColumnInfo("username")
    val username: String
)
