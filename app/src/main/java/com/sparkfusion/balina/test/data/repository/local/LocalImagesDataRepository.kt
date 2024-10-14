package com.sparkfusion.balina.test.data.repository.local

import com.sparkfusion.balina.test.data.datasource.local.ImageDao
import com.sparkfusion.balina.test.domain.mapper.image.local.LocalImageEntityFactory
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.domain.repository.local.ILocalImagesRepository
import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.exception.common.UnexpectedException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalImagesDataRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val imageDao: ImageDao,
    private val localImageEntityFactory: LocalImageEntityFactory
) : ILocalImagesRepository {

    override suspend fun readImagesByUsername(username: String): Answer<List<GetImageModel>> = withContext(ioDispatcher) {
        try {
            Answer.Success(imageDao.readImages(username).map(localImageEntityFactory::mapTo))
        } catch (e: Exception) {
            Answer.Failure(UnexpectedException())
        }
    }
}
