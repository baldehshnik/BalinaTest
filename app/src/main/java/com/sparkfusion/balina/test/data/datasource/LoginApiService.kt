package com.sparkfusion.balina.test.data.datasource

import com.sparkfusion.balina.test.data.entity.user.TokenDataEntity
import com.sparkfusion.balina.test.data.entity.user.UserDataEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {

    @POST("/api/account/signin")
    suspend fun signIn(@Body userDataEntity: UserDataEntity): Response<TokenDataEntity>

    @POST("/api/account/signup")
    suspend fun signUp(@Body userDataEntity: UserDataEntity): Response<TokenDataEntity>
}
