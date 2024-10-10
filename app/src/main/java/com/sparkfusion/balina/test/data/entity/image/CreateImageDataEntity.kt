package com.sparkfusion.balina.test.data.entity.image

import com.google.gson.annotations.SerializedName

data class CreateImageDataEntity(

    @SerializedName("base64Image")
    val base64Image: String,

    @SerializedName("date")
    val date: Long,

    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lng")
    val lng: Double
)
