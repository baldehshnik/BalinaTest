package com.sparkfusion.balina.test.utils.common

import androidx.lifecycle.ViewModel

abstract class CommonViewModel<Intent: UserIntent> : ViewModel() {

    abstract fun handleIntent(intent: Intent)
}
