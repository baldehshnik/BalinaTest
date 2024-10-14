package com.sparkfusion.balina.test.domain.usecase.image

import com.sparkfusion.balina.test.data.datastore.Session
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.domain.repository.local.ILocalImagesRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.exception.common.UnexpectedException
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class LoadLocalImagesUseCase @Inject constructor(
    private val session: Session,
    private val localImagesRepository: ILocalImagesRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): Answer<List<GetImageModel>> = withContext(ioDispatcher) {
        val username = session.readUsername().firstOrNull() ?: return@withContext Answer.Failure(
            UnexpectedException()
        )
        localImagesRepository.readImagesByUsername(username)
    }
}
