package com.sparkfusion.balina.test.data.entity.user

import com.google.gson.annotations.SerializedName

data class UserDataEntity(

    @SerializedName("login")
    val login: String,

    @SerializedName("password")
    val password: String
)
