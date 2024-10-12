package com.sparkfusion.balina.test.ui.window.activity

sealed interface SendImageState {
    data object Loading : SendImageState
    data object Empty : SendImageState
    data object Error : SendImageState
    data object Success : SendImageState
}
