package com.sparkfusion.balina.test.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sparkfusion.balina.test.data.common.ApiResponseHandler
import com.sparkfusion.balina.test.data.common.commonHandleExceptionCode
import com.sparkfusion.balina.test.data.common.safeApiCall
import com.sparkfusion.balina.test.data.datasource.paging.ImagePagingSource
import com.sparkfusion.balina.test.data.datasource.api.ImagesApiService
import com.sparkfusion.balina.test.data.datasource.local.ImageDao
import com.sparkfusion.balina.test.data.datastore.Session
import com.sparkfusion.balina.test.data.entity.image.LocalImageEntity
import com.sparkfusion.balina.test.domain.mapper.image.network.CreateImageFactory
import com.sparkfusion.balina.test.domain.mapper.image.network.GetImageFactory
import com.sparkfusion.balina.test.domain.model.image.CreateImageModel
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.domain.repository.IImagesRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesDataRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val imagesApiService: ImagesApiService,
    private val getImageFactory: GetImageFactory,
    private val createImageFactory: CreateImageFactory,
    private val imagesDao: ImageDao,
    private val session: Session
) : IImagesRepository {

    override suspend fun readImages(): Flow<PagingData<GetImageModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(imagesApiService) }
        ).flow.map { pagingData ->
            pagingData.map { imageModel ->
                val username = session.readUsername().firstOrNull()
                if (username != null) {
                    val localImageEntity = LocalImageEntity(
                        id = imageModel.id,
                        url = imageModel.url,
                        date = imageModel.date,
                        lat = imageModel.lat,
                        lng = imageModel.lng,
                        username = username
                    )
                    imagesDao.insertImage(localImageEntity)
                }
                getImageFactory.mapTo(imageModel)
            }
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
