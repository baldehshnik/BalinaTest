package com.sparkfusion.balina.test.data.entity.comment

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "comment")
data class LocalCommentEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo("text")
    val text: String,

    @ColumnInfo("date")
    val date: Long,

    @ColumnInfo("image_id")
    val imageId: Int
)
