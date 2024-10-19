package com.sparkfusion.balina.test.data.repository.local

import com.sparkfusion.balina.test.data.datasource.local.CommentDao
import com.sparkfusion.balina.test.domain.repository.local.ILocalCommentsRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.exception.common.UnexpectedException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalCommentsDataRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val commentsDao: CommentDao
): ILocalCommentsRepository {

    override suspend fun deleteComments(imageId: Int): Answer<Unit> = withContext(ioDispatcher) {
        try {
            Answer.Success(commentsDao.deleteComments(imageId))
        } catch (e: Exception) {
            Answer.Failure(UnexpectedException())
        }
    }
}
