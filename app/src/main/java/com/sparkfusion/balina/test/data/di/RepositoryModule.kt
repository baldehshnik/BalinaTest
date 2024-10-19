package com.sparkfusion.balina.test.data.di

import com.sparkfusion.balina.test.data.repository.CommentDataRepository
import com.sparkfusion.balina.test.data.repository.ImagesDataRepository
import com.sparkfusion.balina.test.data.repository.LoginDataRepository
import com.sparkfusion.balina.test.data.repository.local.LocalCommentsDataRepository
import com.sparkfusion.balina.test.data.repository.local.LocalImagesDataRepository
import com.sparkfusion.balina.test.domain.repository.ICommentRepository
import com.sparkfusion.balina.test.domain.repository.IImagesRepository
import com.sparkfusion.balina.test.domain.repository.ILoginRepository
import com.sparkfusion.balina.test.domain.repository.local.ILocalCommentsRepository
import com.sparkfusion.balina.test.domain.repository.local.ILocalImagesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    fun bindLocalCommentsDataRepositoryToILocalCommentsRepository(localCommentsDataRepository: LocalCommentsDataRepository): ILocalCommentsRepository

    @Binds
    fun bindLoginDataRepositoryToILoginRepository(loginDataRepository: LoginDataRepository): ILoginRepository

    @Binds
    fun bindImagesDataRepositoryToIImagesRepository(imagesDataRepository: ImagesDataRepository): IImagesRepository

    @Binds
    fun bindCommentDataRepositoryToICommentRepository(commentDataRepository: CommentDataRepository): ICommentRepository

    @Binds
    fun bindLocalImagesDataRepositoryToILocalImagesRepository(localImagesDataRepository: LocalImagesDataRepository): ILocalImagesRepository
}
