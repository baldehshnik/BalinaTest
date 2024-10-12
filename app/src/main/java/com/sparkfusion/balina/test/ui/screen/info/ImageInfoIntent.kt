package com.sparkfusion.balina.test.ui.screen.info

import com.sparkfusion.balina.test.domain.model.comment.GetCommentModel
import com.sparkfusion.balina.test.utils.common.UserIntent

sealed interface ImageInfoIntent : UserIntent {
    data class LoadComments(val imageId: Int) : ImageInfoIntent
    data class SaveComment(val message: String) : ImageInfoIntent
    data class DeleteComment(val comment: GetCommentModel) : ImageInfoIntent
}
