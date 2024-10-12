package com.sparkfusion.balina.test.ui.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SecondsToDateConverter {

    private val dateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())

    fun convert(seconds: Long): String {
        val dateInMillis = seconds * 1000L
        return dateFormat.format(Date(dateInMillis))
    }
}
