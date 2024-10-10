package com.sparkfusion.balina.test.data.di

import com.sparkfusion.balina.test.data.common.AuthInterceptor
import com.sparkfusion.balina.test.data.datasource.CommentApiService
import com.sparkfusion.balina.test.data.datasource.ImagesApiService
import com.sparkfusion.balina.test.data.datasource.LoginApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val BASE_URL = ""

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCommentApiService(retrofit: Retrofit): CommentApiService {
        return retrofit.create(CommentApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideImagesApiService(retrofit: Retrofit): ImagesApiService {
        return retrofit.create(ImagesApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(16, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}
