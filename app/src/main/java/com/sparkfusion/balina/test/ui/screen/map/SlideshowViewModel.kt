package com.sparkfusion.balina.test.ui.screen.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sparkfusion.balina.test.domain.usecase.LoadLocalImagesUseCase
import com.sparkfusion.balina.test.ui.utils.withMainContext
import com.sparkfusion.balina.test.utils.common.CommonViewModel
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SlideshowViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loadLocalImagesUseCase: LoadLocalImagesUseCase
) : CommonViewModel<MapIntent>() {

    private val _images = MutableLiveData<ImagesLoadingState>(ImagesLoadingState.Loading)
    val images: LiveData<ImagesLoadingState> get() = _images

    override fun handleIntent(intent: MapIntent) {
        when (intent) {
            MapIntent.LoadImages -> loadImages()
        }
    }

    private fun loadImages() {
        _images.value = ImagesLoadingState.Loading
        viewModelScope.launch(ioDispatcher) {
            loadLocalImagesUseCase.invoke()
                .onSuccess {
                    withMainContext(_images, ImagesLoadingState.Success(it))
                }
                .onFailure {
                    withMainContext(_images, ImagesLoadingState.Failure)
                }
        }
    }

    init {
        handleIntent(MapIntent.LoadImages)
    }
}
