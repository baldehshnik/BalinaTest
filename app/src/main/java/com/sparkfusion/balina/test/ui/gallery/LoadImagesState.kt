package com.sparkfusion.balina.test.ui.gallery

sealed interface LoadImagesState {
    data object Loading : LoadImagesState
    data object Error : LoadImagesState
    data object Success : LoadImagesState
}
