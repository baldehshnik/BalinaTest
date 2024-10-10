package com.sparkfusion.balina.test.data.entity.user

import com.google.gson.annotations.SerializedName

data class TokenDataEntity(

    @SerializedName("id")
    val userId: Int,

    @SerializedName("login")
    val login: String,

    @SerializedName("token")
    val token: String
)
