package com.sparkfusion.balina.test.domain.usecase.comment

import androidx.paging.PagingData
import com.sparkfusion.balina.test.domain.model.comment.GetCommentModel
import com.sparkfusion.balina.test.domain.repository.ICommentRepository
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class ReadCommentsUseCase @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val commentRepository: ICommentRepository
) {

    suspend operator fun invoke(imageId: Int): Flow<PagingData<GetCommentModel>> = withContext(ioDispatcher) {
        commentRepository.readComments(imageId)
    }
}
