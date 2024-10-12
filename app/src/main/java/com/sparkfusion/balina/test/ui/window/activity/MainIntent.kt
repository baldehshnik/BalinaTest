package com.sparkfusion.balina.test.ui.window.activity

import android.graphics.Bitmap
import com.sparkfusion.balina.test.utils.common.UserIntent

sealed interface MainIntent : UserIntent {
    data class SendImage(val image: Bitmap) : MainIntent
    data class ChangeCurrentLat(val value: Double) : MainIntent
    data class ChangeCurrentLng(val value: Double) : MainIntent
}
