package com.sparkfusion.balina.test.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sparkfusion.balina.test.data.common.ApiResponseHandler
import com.sparkfusion.balina.test.data.common.commonHandleExceptionCode
import com.sparkfusion.balina.test.data.common.safeApiCall
import com.sparkfusion.balina.test.data.datasource.ImagePagingSource
import com.sparkfusion.balina.test.data.datasource.ImagesApiService
import com.sparkfusion.balina.test.domain.mapper.image.CreateImageFactory
import com.sparkfusion.balina.test.domain.mapper.image.GetImageFactory
import com.sparkfusion.balina.test.domain.model.image.CreateImageModel
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.domain.repository.IImagesRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesDataRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val imagesApiService: ImagesApiService,
    private val getImageFactory: GetImageFactory,
    private val createImageFactory: CreateImageFactory
) : IImagesRepository {

    override suspend fun readImages(): Flow<PagingData<GetImageModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(imagesApiService) }
        ).flow.map {
            it.map(getImageFactory::mapTo)
        }
    }

    override suspend fun saveImage(createImageModel: CreateImageModel): Answer<Unit> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(
                imagesApiService.saveImage(createImageFactory.mapFrom(createImageModel)),
                ::commonHandleExceptionCode
            ).handleFetchedData()
        }

    override suspend fun deleteImage(id: Int): Answer<Unit> = safeApiCall(ioDispatcher) {
        ApiResponseHandler(
            imagesApiService.removeImage(id),
            ::commonHandleExceptionCode
        ).handleFetchedData()
    }
}
