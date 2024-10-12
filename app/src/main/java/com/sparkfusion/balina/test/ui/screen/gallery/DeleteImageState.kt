package com.sparkfusion.balina.test.ui.screen.gallery

sealed interface DeleteImageState {
    data object Empty: DeleteImageState
    data object Success : DeleteImageState
    data object Failure : DeleteImageState
}
