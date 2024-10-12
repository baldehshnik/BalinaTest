package com.sparkfusion.balina.test.ui.utils

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore

internal class UriCreator(private val applicationContext: Context) {

    fun createImageUri(): Uri? {
        val contentResolver = applicationContext.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "new_image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }
}
