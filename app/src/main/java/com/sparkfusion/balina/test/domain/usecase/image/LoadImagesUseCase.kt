package com.sparkfusion.balina.test.domain.usecase.image

import androidx.paging.PagingData
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.domain.repository.IImagesRepository
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class LoadImagesUseCase @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val imagesRepository: IImagesRepository
) {

    suspend operator fun invoke(): Flow<PagingData<GetImageModel>> = withContext(ioDispatcher) {
        imagesRepository.readImages()
    }
}
