package com.sparkfusion.balina.test.ui.screen.map

import com.sparkfusion.balina.test.domain.model.image.GetImageModel

sealed interface ImagesLoadingState {
    data object Loading: ImagesLoadingState
    data object Failure : ImagesLoadingState
    data class Success(val data: List<GetImageModel>) : ImagesLoadingState
}
