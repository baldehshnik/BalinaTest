package com.sparkfusion.balina.test.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sparkfusion.balina.test.data.entity.comment.LocalCommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: LocalCommentEntity): Long

    @Query("DELETE FROM comment WHERE image_id = :imageId")
    suspend fun deleteComments(imageId: Int)
}
