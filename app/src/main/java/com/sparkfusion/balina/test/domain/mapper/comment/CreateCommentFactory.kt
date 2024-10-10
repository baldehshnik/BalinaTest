package com.sparkfusion.balina.test.domain.mapper.comment

import com.sparkfusion.balina.test.data.entity.comment.CreateCommentDataEntity
import com.sparkfusion.balina.test.domain.model.comment.CreateCommentModel
import com.sparkfusion.balina.test.utils.MapFactory
import javax.inject.Inject

class CreateCommentFactory @Inject constructor() : MapFactory<CreateCommentDataEntity, CreateCommentModel> {

    override fun mapTo(input: CreateCommentDataEntity): CreateCommentModel = with(input) {
        CreateCommentModel(id, date, text)
    }

    override fun mapFrom(input: CreateCommentModel): CreateCommentDataEntity = with(input) {
        CreateCommentDataEntity(id, date, text)
    }
}