package com.sparkfusion.balina.test.domain.repository.local

import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.utils.common.Answer

interface ILocalImagesRepository {

    suspend fun readImagesByUsername(username: String): Answer<List<GetImageModel>>
}
