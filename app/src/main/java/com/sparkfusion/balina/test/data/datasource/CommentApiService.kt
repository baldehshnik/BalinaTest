package com.sparkfusion.balina.test.data.datasource

import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Path

interface CommentApiService {

    @DELETE("/api/image/{imageId}/comment/{commentId}")
    suspend fun deleteComment(
        @Path("imageId") imageId: Int,
        @Path("commentId") commentId: Int
    ): Response<Unit>
}
