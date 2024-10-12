package com.sparkfusion.balina.test.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sparkfusion.balina.test.data.common.ApiResponseHandler
import com.sparkfusion.balina.test.data.common.commonHandleExceptionCode
import com.sparkfusion.balina.test.data.common.safeApiCall
import com.sparkfusion.balina.test.data.datasource.CommentApiService
import com.sparkfusion.balina.test.data.datasource.CommentsPagingSource
import com.sparkfusion.balina.test.domain.mapper.comment.CreateCommentFactory
import com.sparkfusion.balina.test.domain.mapper.comment.GetCommentFactory
import com.sparkfusion.balina.test.domain.model.comment.CreateCommentModel
import com.sparkfusion.balina.test.domain.model.comment.GetCommentModel
import com.sparkfusion.balina.test.domain.repository.ICommentRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentDataRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val commentApiService: CommentApiService,
    private val getCommentFactory: GetCommentFactory,
    private val createCommentFactory: CreateCommentFactory
) : ICommentRepository {

    override suspend fun saveComment(imageId: Int, comment: CreateCommentModel): Answer<Unit> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(
                commentApiService.saveComment(imageId, createCommentFactory.mapFrom(comment)),
                ::commonHandleExceptionCode
            ).handleFetchedData()
        }

    override suspend fun readComments(imageId: Int): Flow<PagingData<GetCommentModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CommentsPagingSource(commentApiService, imageId) }
        ).flow.map { pagingData ->
            pagingData.map(getCommentFactory::mapTo)
        }
    }

    override suspend fun deleteComment(imageId: Int, commentId: Int): Answer<Unit> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(
                commentApiService.deleteComment(imageId, commentId),
                ::commonHandleExceptionCode
            ).handleFetchedData()
        }
}
