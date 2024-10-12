package com.sparkfusion.balina.test.domain.repository

import androidx.paging.PagingData
import com.sparkfusion.balina.test.domain.model.comment.CreateCommentModel
import com.sparkfusion.balina.test.domain.model.comment.GetCommentModel
import com.sparkfusion.balina.test.utils.common.Answer
import kotlinx.coroutines.flow.Flow

interface ICommentRepository {

    suspend fun readComments(imageId: Int): Flow<PagingData<GetCommentModel>>

    suspend fun deleteComment(imageId: Int, commentId: Int): Answer<Unit>

    suspend fun saveComment(imageId: Int, comment: CreateCommentModel): Answer<Unit>
}
