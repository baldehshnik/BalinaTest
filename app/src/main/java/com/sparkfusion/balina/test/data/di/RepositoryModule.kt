package com.sparkfusion.balina.test.data.di

import com.sparkfusion.balina.test.data.repository.CommentDataRepository
import com.sparkfusion.balina.test.data.repository.ImagesDataRepository
import com.sparkfusion.balina.test.data.repository.LoginDataRepository
import com.sparkfusion.balina.test.domain.repository.ICommentRepository
import com.sparkfusion.balina.test.domain.repository.IImagesRepository
import com.sparkfusion.balina.test.domain.repository.ILoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindLoginDataRepositoryToILoginRepository(loginDataRepository: LoginDataRepository): ILoginRepository

    @Binds
    fun bindImagesDataRepositoryToIImagesRepository(imagesDataRepository: ImagesDataRepository): IImagesRepository

    @Binds
    fun bindCommentDataRepositoryToICommentRepository(commentDataRepository: CommentDataRepository): ICommentRepository
}
