package com.sparkfusion.balina.test.data.di

import android.content.Context
import androidx.room.Room
import com.sparkfusion.balina.test.data.datasource.local.AppDatabase
import com.sparkfusion.balina.test.data.datasource.local.CommentDao
import com.sparkfusion.balina.test.data.datasource.local.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideImageDao(room: AppDatabase): ImageDao {
        return room.imageDao()
    }

    @Singleton
    @Provides
    fun provideCommentDao(room: AppDatabase): CommentDao {
        return room.commentDao()
    }
}
