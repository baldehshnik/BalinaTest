package com.sparkfusion.balina.test.ui.screen.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sparkfusion.balina.test.domain.model.image.GetImageModel
import com.sparkfusion.balina.test.domain.usecase.DeleteImageUseCase
import com.sparkfusion.balina.test.domain.usecase.LoadImagesUseCase
import com.sparkfusion.balina.test.ui.utils.withMainContext
import com.sparkfusion.balina.test.utils.common.CommonViewModel
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loadImagesUseCase: LoadImagesUseCase,
    private val deleteImageUseCase: DeleteImageUseCase
) : CommonViewModel<GalleryLoadImagesIntent>() {

    private val _imagesLiveData = MutableLiveData<PagingData<GetImageModel>>()
    val imagesLiveData: LiveData<PagingData<GetImageModel>> get() = _imagesLiveData

    private val _deleteImageLiveData = MutableLiveData<DeleteImageState>(DeleteImageState.Empty)
    val deleteImageLiveData: LiveData<DeleteImageState> get() = _deleteImageLiveData

    override fun handleIntent(intent: GalleryLoadImagesIntent) {
        when (intent) {
            GalleryLoadImagesIntent.LoadImages -> loadImages()
            is GalleryLoadImagesIntent.DeleteImage -> deleteImage(intent.model)
        }
    }

    private fun loadImages() {
        viewModelScope.launch(ioDispatcher) {
            val pagingDataFlow = loadImagesUseCase.invoke().cachedIn(viewModelScope)
            pagingDataFlow.collect { pagingData ->
                _imagesLiveData.postValue(pagingData)
            }
        }
    }

    private fun deleteImage(model: GetImageModel) {
        viewModelScope.launch(ioDispatcher) {
            deleteImageUseCase.invoke(model)
                .onSuccess {
                    withMainContext(_deleteImageLiveData, DeleteImageState.Success)
                }
                .onFailure {
                    withMainContext(_deleteImageLiveData, DeleteImageState.Failure)
                }
        }
    }

    init {
        handleIntent(GalleryLoadImagesIntent.LoadImages)
    }
}
