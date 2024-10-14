package com.sparkfusion.balina.test.domain.mapper.image.local

import com.sparkfusion.balina.test.data.entity.image.LocalImageEntity
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.utils.MapFactory
import javax.inject.Inject

class LocalImageEntityFactory @Inject constructor() : MapFactory<LocalImageEntity, GetImageModel> {

    override fun mapTo(input: LocalImageEntity): GetImageModel = with(input) {
        GetImageModel(id, url, date, lat, lng)
    }

    override fun mapFrom(input: GetImageModel): LocalImageEntity = with(input) {
        LocalImageEntity(id, url, date, lat, lng, "empty")
    }
}
