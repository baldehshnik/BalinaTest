package com.sparkfusion.balina.test.domain.model.image

data class GetImageModel(
    val id: Int,
    val url: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)
