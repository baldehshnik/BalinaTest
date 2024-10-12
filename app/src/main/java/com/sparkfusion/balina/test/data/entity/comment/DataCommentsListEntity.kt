package com.sparkfusion.balina.test.data.entity.comment

import com.google.gson.annotations.SerializedName

data class DataCommentsListEntity(

    @SerializedName("data")
    val data: List<GetCommentDataEntity>
)
