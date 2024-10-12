package com.sparkfusion.balina.test.ui.launcher

import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.sparkfusion.balina.test.ui.window.activity.MainActivity

fun MainActivity.registerLocationSettingLauncher(
    handler: (ActivityResult) -> Unit
): ActivityResultLauncher<IntentSenderRequest> {
    return registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        handler(result)
    }
}
