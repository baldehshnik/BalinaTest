package com.sparkfusion.balina.test.ui.screen.info

sealed interface DeleteCommentState {
    data object Empty : DeleteCommentState
    data object ImageNotFound : DeleteCommentState
    data object Success : DeleteCommentState
    data object Failure : DeleteCommentState
}
