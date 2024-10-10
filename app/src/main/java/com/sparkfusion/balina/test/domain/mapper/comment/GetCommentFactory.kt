package com.sparkfusion.balina.test.domain.mapper.comment

import com.sparkfusion.balina.test.data.entity.comment.GetCommentDataEntity
import com.sparkfusion.balina.test.domain.model.comment.GetCommentModel
import com.sparkfusion.balina.test.utils.MapFactory
import javax.inject.Inject

class GetCommentFactory @Inject constructor() : MapFactory<GetCommentDataEntity, GetCommentModel> {

    override fun mapTo(input: GetCommentDataEntity): GetCommentModel = with(input) {
        GetCommentModel(text)
    }

    override fun mapFrom(input: GetCommentModel): GetCommentDataEntity = with(input) {
        GetCommentDataEntity(text)
    }
}