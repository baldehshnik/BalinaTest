package com.sparkfusion.balina.test.data.datasource.api

import com.sparkfusion.balina.test.data.entity.user.DataTokenEntity
import com.sparkfusion.balina.test.data.entity.user.UserDataEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {

    @POST("/api/account/signin")
    suspend fun signIn(@Body userDataEntity: UserDataEntity): Response<DataTokenEntity>

    @POST("/api/account/signup")
    suspend fun signUp(@Body userDataEntity: UserDataEntity): Response<DataTokenEntity>
}
