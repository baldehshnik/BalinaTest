package com.sparkfusion.balina.test.domain.repository

import com.sparkfusion.balina.test.domain.model.user.TokenModel
import com.sparkfusion.balina.test.domain.model.user.UserModel
import com.sparkfusion.balina.test.utils.common.Answer

interface ILoginRepository {

    suspend fun signIn(userModel: UserModel): Answer<TokenModel>

    suspend fun signUp(userModel: UserModel): Answer<TokenModel>
}
