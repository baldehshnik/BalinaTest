package com.sparkfusion.balina.test.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sparkfusion.balina.test.data.entity.comment.LocalCommentEntity
import com.sparkfusion.balina.test.data.entity.image.LocalImageEntity

@Database(entities = [LocalImageEntity::class, LocalCommentEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
    abstract fun commentDao(): CommentDao

    companion object {
        const val NAME = "app_database"
    }
}
