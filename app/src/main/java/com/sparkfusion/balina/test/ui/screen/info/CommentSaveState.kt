package com.sparkfusion.balina.test.ui.screen.info

sealed interface CommentSaveState {
    data object Empty : CommentSaveState
    data object Success : CommentSaveState
    data object Failure : CommentSaveState
    data object ImageNotFound : CommentSaveState
}
