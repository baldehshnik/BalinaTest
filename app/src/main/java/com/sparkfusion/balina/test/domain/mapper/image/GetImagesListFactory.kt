package com.sparkfusion.balina.test.domain.mapper.image

import com.sparkfusion.balina.test.data.entity.image.GetImageDataEntity
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.utils.MapFactory
import javax.inject.Inject

class GetImagesListFactory @Inject constructor(
    private val getImageFactory: GetImageFactory
) : MapFactory<List<GetImageDataEntity>, List<GetImageModel>> {
    override fun mapTo(input: List<GetImageDataEntity>): List<GetImageModel> {
        return input.map {
            getImageFactory.mapTo(it)
        }
    }

    override fun mapFrom(input: List<GetImageModel>): List<GetImageDataEntity> {
        return input.map {
            getImageFactory.mapFrom(it)
        }
    }
}
