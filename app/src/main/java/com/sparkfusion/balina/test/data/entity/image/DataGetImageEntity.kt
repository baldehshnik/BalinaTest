package com.sparkfusion.balina.test.data.entity.image

import com.google.gson.annotations.SerializedName

data class DataGetImageEntity(

    @SerializedName("data")
    val data: List<GetImageDataEntity>
)
