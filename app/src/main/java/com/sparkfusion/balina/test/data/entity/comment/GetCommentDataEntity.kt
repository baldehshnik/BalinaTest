package com.sparkfusion.balina.test.data.entity.comment

import com.google.gson.annotations.SerializedName

data class GetCommentDataEntity(

    @SerializedName("id")
    val id: Int,

    @SerializedName("text")
    val text: String,

    @SerializedName("date")
    val date: Long
)
