package com.sparkfusion.balina.test.domain.model.image

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetImageModel(
    val id: Int,
    val url: String,
    val date: Long,
    val lat: Double,
    val lng: Double
): Parcelable
