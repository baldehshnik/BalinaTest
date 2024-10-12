package com.sparkfusion.balina.test.ui.launcher

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.sparkfusion.balina.test.ui.window.activity.MainActivity

fun MainActivity.registerTakePictureLauncher(
    handler: (isSuccess: Boolean) -> Unit
): ActivityResultLauncher<Uri> {
    return registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        handler(success)
    }
}
