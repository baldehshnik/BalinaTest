package com.sparkfusion.balina.test.data.repository

import com.sparkfusion.balina.test.data.common.ApiResponseHandler
import com.sparkfusion.balina.test.data.common.commonHandleExceptionCode
import com.sparkfusion.balina.test.data.common.safeApiCall
import com.sparkfusion.balina.test.data.datasource.CommentApiService
import com.sparkfusion.balina.test.domain.repository.ICommentRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentDataRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val commentApiService: CommentApiService
) : ICommentRepository {

    override suspend fun deleteComment(imageId: Int, commentId: Int): Answer<Unit> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(
                commentApiService.deleteComment(imageId, commentId),
                ::commonHandleExceptionCode
            ).handleFetchedData()
        }
}
