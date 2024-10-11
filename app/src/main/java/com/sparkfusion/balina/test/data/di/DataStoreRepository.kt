package com.sparkfusion.balina.test.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATA_STORE_NAME = "authentication_store"
private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATA_STORE_NAME
)

@Module
@InstallIn(SingletonComponent::class)
object DataStoreRepository {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.userDataStore
    }
}
