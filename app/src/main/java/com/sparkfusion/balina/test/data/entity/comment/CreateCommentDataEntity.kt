package com.sparkfusion.balina.test.data.entity.comment

import com.google.gson.annotations.SerializedName

data class CreateCommentDataEntity(

    @SerializedName("id")
    val id: Int,

    @SerializedName("date")
    val date: Long,

    @SerializedName("text")
    val text: String
)
