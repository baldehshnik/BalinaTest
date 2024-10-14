package com.sparkfusion.balina.test.domain.usecase.login

import com.sparkfusion.balina.test.data.datastore.Session
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.exception.common.UnexpectedException
import com.sparkfusion.balina.test.utils.exception.datastore.FailedDataStoreOperationException
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetUsernameUseCase @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val session: Session
) {

    suspend operator fun invoke(): Answer<String> = withContext(ioDispatcher) {
        try {
            val username: String? = session.readUsername().firstOrNull()
            if (username != null) return@withContext Answer.Success(username)
        } catch (e: FailedDataStoreOperationException) {
            Answer.Failure(e)
        }

        Answer.Failure(UnexpectedException())
    }
}
