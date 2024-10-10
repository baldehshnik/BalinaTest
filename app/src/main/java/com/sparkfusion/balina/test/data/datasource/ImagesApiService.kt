package com.sparkfusion.balina.test.data.datasource

import com.sparkfusion.balina.test.data.entity.image.CreateImageDataEntity
import com.sparkfusion.balina.test.data.entity.image.GetImageDataEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ImagesApiService {

    @GET("/api/image")
    suspend fun readImages(): Response<List<GetImageDataEntity>>

    @POST("/api/image")
    suspend fun saveImage(@Body image: CreateImageDataEntity): Response<Unit>

    @DELETE("/api/image/{id}")
    suspend fun removeImage(@Path("id") id: Int): Response<Unit>
}
