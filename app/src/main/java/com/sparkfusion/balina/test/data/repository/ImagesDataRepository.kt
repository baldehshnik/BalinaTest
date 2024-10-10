package com.sparkfusion.balina.test.data.repository

import com.sparkfusion.balina.test.data.common.ApiListResponseHandler
import com.sparkfusion.balina.test.data.common.ApiResponseHandler
import com.sparkfusion.balina.test.data.common.commonHandleExceptionCode
import com.sparkfusion.balina.test.data.common.safeApiCall
import com.sparkfusion.balina.test.data.datasource.ImagesApiService
import com.sparkfusion.balina.test.domain.mapper.image.CreateImageFactory
import com.sparkfusion.balina.test.domain.mapper.image.GetImagesListFactory
import com.sparkfusion.balina.test.domain.model.image.CreateImageModel
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.domain.repository.IImagesRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesDataRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val imagesApiService: ImagesApiService,
    private val getImagesListFactory: GetImagesListFactory,
    private val createImageFactory: CreateImageFactory
) : IImagesRepository {

    override suspend fun readImages(): Answer<List<GetImageModel>> = safeApiCall(ioDispatcher) {
        ApiListResponseHandler(imagesApiService.readImages(), ::commonHandleExceptionCode)
            .handleFetchedData()
            .suspendMap(getImagesListFactory::mapTo)
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
















