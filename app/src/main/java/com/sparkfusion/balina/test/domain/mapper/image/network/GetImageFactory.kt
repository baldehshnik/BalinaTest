package com.sparkfusion.balina.test.domain.mapper.image.network

import com.sparkfusion.balina.test.data.entity.image.GetImageDataEntity
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.utils.MapFactory
import javax.inject.Inject

class GetImageFactory @Inject constructor() : MapFactory<GetImageDataEntity, GetImageModel> {

    override fun mapTo(input: GetImageDataEntity): GetImageModel = with(input) {
        GetImageModel(id, url, date, lat, lng)
    }

    override fun mapFrom(input: GetImageModel): GetImageDataEntity = with(input) {
        GetImageDataEntity(id, url, date, lat, lng)
    }
}
