package com.sparkfusion.balina.test.domain.model.image

data class CreateImageModel(
    val base64Image: String,
    val date: Long,
    val lat: Double,
    val lng: Double
)
