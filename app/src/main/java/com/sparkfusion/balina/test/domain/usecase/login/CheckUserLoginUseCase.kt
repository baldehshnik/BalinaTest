package com.sparkfusion.balina.test.domain.usecase.login

import com.sparkfusion.balina.test.data.datastore.Session
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.exception.datastore.FailedDataStoreOperationException
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class CheckUserLoginUseCase @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val session: Session
) {

    suspend operator fun invoke(): Answer<Boolean> = withContext(ioDispatcher) {
        try {
            val token = session.readUserToken().firstOrNull()
            Answer.Success(token?.isNotEmpty() == true)
        } catch (e: FailedDataStoreOperationException) {
            Answer.Failure(e)
        }
    }
}
