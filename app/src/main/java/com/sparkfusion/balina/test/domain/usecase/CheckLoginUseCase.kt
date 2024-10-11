package com.sparkfusion.balina.test.domain.usecase

import com.sparkfusion.balina.test.data.datastore.Session
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.exception.UnexpectedException
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class CheckLoginUseCase @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val session: Session
) {

    suspend operator fun invoke(): Answer<Unit> = withContext(ioDispatcher) {
        var isAuth = true
        session.readUsername().collectLatest {
            if (it.isEmpty()) isAuth = false
        }
        session.readUserToken().collectLatest {
            if (it.isEmpty()) isAuth = false
        }

        if (isAuth) Answer.Success(Unit) else Answer.Failure(UnexpectedException())
    }
}
