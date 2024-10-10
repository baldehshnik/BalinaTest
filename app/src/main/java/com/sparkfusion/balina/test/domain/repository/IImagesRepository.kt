package com.sparkfusion.balina.test.domain.repository

import com.sparkfusion.balina.test.domain.model.image.CreateImageModel
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.utils.common.Answer

interface IImagesRepository {

    suspend fun readImages(): Answer<List<GetImageModel>>

    suspend fun saveImage(createImageModel: CreateImageModel): Answer<Unit>

    suspend fun deleteImage(id: Int): Answer<Unit>
}