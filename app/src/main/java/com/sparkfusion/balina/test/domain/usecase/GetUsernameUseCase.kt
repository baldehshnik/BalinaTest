package com.sparkfusion.balina.test.domain.usecase

import com.sparkfusion.balina.test.data.datastore.Session
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.exception.FailedDataStoreOperationException
import com.sparkfusion.balina.test.utils.exception.UnexpectedException
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetUsernameUseCase @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val session: Session
) {

    suspend operator fun invoke(): Answer<String> = withContext(ioDispatcher) {
        try {
            var username: String? = null
            session.readUsername().collectLatest {
                username = it
            }

            if (username != null) Answer.Success(username)
        } catch (e: FailedDataStoreOperationException) {
            Answer.Failure(e)
        }

        Answer.Failure(UnexpectedException())
    }
}
