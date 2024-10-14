package com.sparkfusion.balina.test.ui.window.activity

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sparkfusion.balina.test.domain.model.image.CreateImageModel
import com.sparkfusion.balina.test.domain.repository.IImagesRepository
import com.sparkfusion.balina.test.domain.usecase.login.GetUsernameUseCase
import com.sparkfusion.balina.test.ui.utils.withMainContext
import com.sparkfusion.balina.test.utils.common.CommonViewModel
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.Base64
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val imagesRepository: IImagesRepository,
    private val loadUsernameUseCase: GetUsernameUseCase
) : CommonViewModel<MainIntent>() {

    private val _sendImageState = MutableLiveData<SendImageState>(SendImageState.Empty)
    val sendImageState: LiveData<SendImageState> get() = _sendImageState

    private val _usernameState = MutableLiveData<LoadUsernameState>()
    val usernameState: LiveData<LoadUsernameState> get() = _usernameState

    private var currentLat: Double = 0.0
    private var currentLng: Double = 0.0

    override fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.SendImage -> sendImage(intent.image)
            is MainIntent.ChangeCurrentLat -> currentLat = intent.value
            is MainIntent.ChangeCurrentLng -> currentLng = intent.value
            MainIntent.LoadUsername -> loadUsername()
        }
    }

    private fun loadUsername() {
        viewModelScope.launch(ioDispatcher) {
            loadUsernameUseCase.invoke()
                .onSuccess {
                    withMainContext(_usernameState, LoadUsernameState.Success(it))
                }
                .onFailure {
                    withMainContext(_usernameState, LoadUsernameState.Failure)
                }
        }
    }

    private fun sendImage(image: Bitmap) {
        _sendImageState.value = SendImageState.Loading
        viewModelScope.launch(ioDispatcher) {
            val model = CreateImageModel(
                base64Image = convertBitmapToBase64(image),
                date = System.currentTimeMillis() / 1000,
                lat = currentLat,
                lng = currentLng
            )

            imagesRepository.saveImage(model)
                .onSuccess {
                    withMainContext(_sendImageState, SendImageState.Success)
                }
                .onFailure {
                    withMainContext(_sendImageState, SendImageState.Error)
                }
        }
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val resizedBitmap = resizeBitmap(bitmap)
        val byteArrayOutputStream = ByteArrayOutputStream()

        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream)

        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.getEncoder().encodeToString(byteArray)
    }

    private fun resizeBitmap(bitmap: Bitmap): Bitmap {
        val maxSize = 1080
        val width = bitmap.width
        val height = bitmap.height
        val bitmapRatio: Float = width.toFloat() / height.toFloat()

        return if (bitmapRatio > 1) {
            Bitmap.createScaledBitmap(bitmap, maxSize, (maxSize / bitmapRatio).toInt(), true)
        } else {
            Bitmap.createScaledBitmap(bitmap, (maxSize * bitmapRatio).toInt(), maxSize, true)
        }
    }

    init {
        handleIntent(MainIntent.LoadUsername)
    }
}
