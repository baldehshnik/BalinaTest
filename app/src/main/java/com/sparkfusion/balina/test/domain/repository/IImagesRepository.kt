package com.sparkfusion.balina.test.domain.repository

import androidx.paging.PagingData
import com.sparkfusion.balina.test.domain.model.image.CreateImageModel
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.utils.common.Answer
import kotlinx.coroutines.flow.Flow

interface IImagesRepository {

    suspend fun readImages(): Flow<PagingData<GetImageModel>>

    suspend fun saveImage(createImageModel: CreateImageModel): Answer<Unit>

    suspend fun deleteImage(id: Int): Answer<Unit>
}
