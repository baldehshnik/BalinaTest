package com.sparkfusion.balina.test.domain.usecase

import com.sparkfusion.balina.test.data.datastore.Session
import com.sparkfusion.balina.test.domain.model.user.UserModel
import com.sparkfusion.balina.test.domain.repository.ILoginRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.exception.FailedDataStoreOperationException
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class LoginUseCase @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loginRepository: ILoginRepository,
    private val session: Session
) {

    suspend operator fun invoke(user: UserModel): Answer<Unit> = withContext(ioDispatcher) {
        try {
            session.readUsername().collectLatest {
                if (it.isNotEmpty()) session.clearUsername()
            }
            session.readUserToken().collectLatest {
                if (it.isNotEmpty()) session.clearUserToken()
            }

            loginRepository.signIn(user)
                .onSuccess { tokenModel ->
                    session.saveUserUsername(tokenModel.login)
                    session.saveUserToken(tokenModel.token)
                }
                .onFailure {
                    return@withContext Answer.Failure(it)
                }
        } catch (e: FailedDataStoreOperationException) {
            return@withContext Answer.Failure(e)
        }

        Answer.Success(Unit)
    }
}


















