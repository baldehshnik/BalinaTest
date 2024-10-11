package com.sparkfusion.balina.test.data.entity.user

import com.google.gson.annotations.SerializedName

data class DataTokenEntity(

    @SerializedName("data")
    val data: TokenDataEntity
)
