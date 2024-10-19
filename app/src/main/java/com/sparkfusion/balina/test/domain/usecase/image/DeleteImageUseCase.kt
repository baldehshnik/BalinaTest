package com.sparkfusion.balina.test.domain.usecase.image

import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.domain.repository.IImagesRepository
import com.sparkfusion.balina.test.domain.repository.local.ILocalCommentsRepository
import com.sparkfusion.balina.test.domain.repository.local.ILocalImagesRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class DeleteImageUseCase @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val imagesRepository: IImagesRepository,
    private val localImagesRepository: ILocalImagesRepository,
    private val localCommentsRepository: ILocalCommentsRepository
) {

    suspend operator fun invoke(model: GetImageModel): Answer<Unit> = withContext(ioDispatcher) {
        val answer = imagesRepository.deleteImage(model.id)
        answer.onSuccess {
            localImagesRepository.deleteImage(model.id)
            localCommentsRepository.deleteComments(model.id)
        }

        answer
    }
}
