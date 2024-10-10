package com.sparkfusion.balina.test.data.entity.image

import com.google.gson.annotations.SerializedName

data class GetImageDataEntity(

    @SerializedName("id")
    val id: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("date")
    val date: Long,

    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lng")
    val lng: Double
)
