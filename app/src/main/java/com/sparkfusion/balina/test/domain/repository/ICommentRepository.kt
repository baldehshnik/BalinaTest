package com.sparkfusion.balina.test.domain.repository

import com.sparkfusion.balina.test.utils.common.Answer

interface ICommentRepository {

    suspend fun deleteComment(imageId: Int, commentId: Int): Answer<Unit>
}
