package com.sparkfusion.balina.test.data.entity.comment

import com.google.gson.annotations.SerializedName

data class CreateCommentDataEntity(

    @SerializedName("text")
    val text: String
)
