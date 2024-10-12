package com.sparkfusion.balina.test.ui.screen.gallery

import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.utils.common.UserIntent

interface GalleryLoadImagesIntent : UserIntent {
    data object LoadImages : GalleryLoadImagesIntent
    data class DeleteImage(val model: GetImageModel) : GalleryLoadImagesIntent
}
