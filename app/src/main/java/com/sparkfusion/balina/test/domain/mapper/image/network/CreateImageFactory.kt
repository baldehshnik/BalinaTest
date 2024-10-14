package com.sparkfusion.balina.test.domain.mapper.image.network

import com.sparkfusion.balina.test.data.entity.image.CreateImageDataEntity
import com.sparkfusion.balina.test.domain.model.image.CreateImageModel
import com.sparkfusion.balina.test.utils.MapFactory
import javax.inject.Inject

class CreateImageFactory @Inject constructor() : MapFactory<CreateImageDataEntity, CreateImageModel> {

    override fun mapTo(input: CreateImageDataEntity): CreateImageModel = with(input) {
        CreateImageModel(base64Image, date, lat, lng)
    }

    override fun mapFrom(input: CreateImageModel): CreateImageDataEntity = with(input) {
        CreateImageDataEntity(base64Image, date, lat, lng)
    }
}
