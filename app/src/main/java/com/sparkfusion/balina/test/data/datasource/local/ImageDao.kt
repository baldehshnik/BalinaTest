package com.sparkfusion.balina.test.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sparkfusion.balina.test.data.entity.image.LocalImageEntity

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: LocalImageEntity)

    @Query("SELECT * FROM images WHERE username = :username")
    suspend fun readImages(username: String): List<LocalImageEntity>

    @Query("DELETE FROM images WHERE id = :imageId")
    suspend fun deleteImage(imageId: Int)
}
