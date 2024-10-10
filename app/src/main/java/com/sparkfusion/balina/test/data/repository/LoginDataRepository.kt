package com.sparkfusion.balina.test.data.repository

import com.sparkfusion.balina.test.data.common.ApiResponseHandler
import com.sparkfusion.balina.test.data.common.handleSignInExceptionCode
import com.sparkfusion.balina.test.data.common.handleSignUpExceptionCode
import com.sparkfusion.balina.test.data.common.safeApiCall
import com.sparkfusion.balina.test.data.datasource.LoginApiService
import com.sparkfusion.balina.test.domain.mapper.user.TokenFactory
import com.sparkfusion.balina.test.domain.mapper.user.UserFactory
import com.sparkfusion.balina.test.domain.model.user.TokenModel
import com.sparkfusion.balina.test.domain.model.user.UserModel
import com.sparkfusion.balina.test.domain.repository.ILoginRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginDataRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val service: LoginApiService,
    private val tokenFactory: TokenFactory,
    private val userFactory: UserFactory
) : ILoginRepository {

    override suspend fun signIn(userModel: UserModel): Answer<TokenModel> = safeApiCall(ioDispatcher) {
        ApiResponseHandler(service.signIn(userFactory.mapFrom(userModel)), ::handleSignInExceptionCode)
            .handleFetchedData()
            .suspendMap(tokenFactory::mapTo)
    }

    override suspend fun signUp(userModel: UserModel): Answer<TokenModel> = safeApiCall(ioDispatcher) {
        ApiResponseHandler(service.signUp(userFactory.mapFrom(userModel)), ::handleSignUpExceptionCode)
            .handleFetchedData()
            .suspendMap(tokenFactory::mapTo)
    }
}


















