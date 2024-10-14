package com.sparkfusion.balina.test.data.datasource.api

import com.sparkfusion.balina.test.data.entity.comment.CreateCommentDataEntity
import com.sparkfusion.balina.test.data.entity.comment.DataCommentsListEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommentApiService {

    @GET("/api/image/{imageId}/comment")
    suspend fun readComments(
        @Path("imageId") imageId: Int,
        @Query("page") page: Int
    ): Response<DataCommentsListEntity>

    @DELETE("/api/image/{imageId}/comment/{commentId}")
    suspend fun deleteComment(
        @Path("imageId") imageId: Int,
        @Path("commentId") commentId: Int
    ): Response<Unit>

    @POST("/api/image/{imageId}/comment")
    suspend fun saveComment(
        @Path("imageId") imageId: Int,
        @Body comment: CreateCommentDataEntity
    ): Response<Unit>
}
