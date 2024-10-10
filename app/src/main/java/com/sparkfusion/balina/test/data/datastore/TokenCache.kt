package com.sparkfusion.balina.test.data.datastore

import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.exception.FailedDataStoreOperationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenCache @Inject constructor(
    private val session: Session,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {

    private var token: String? = null

    suspend fun getToken(): String? = withContext(ioDispatcher) {
        try {
            if (token == null) {
                token = session.readUserToken().firstOrNull()
            }

            token
        } catch (ignore: FailedDataStoreOperationException) {
            null
        }
    }
}