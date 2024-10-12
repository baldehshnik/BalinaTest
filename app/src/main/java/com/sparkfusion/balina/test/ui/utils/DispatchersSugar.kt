package com.sparkfusion.balina.test.ui.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <P> withMainContext(liveData: MutableLiveData<P>, value: P) = withContext(Dispatchers.Main) {
    liveData.value = value
}
