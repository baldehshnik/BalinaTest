package com.sparkfusion.balina.test.ui.utils

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun View.shortSnackbar(@StringRes id: Int) {
    Snackbar.make(this, id, Snackbar.LENGTH_SHORT).show()
}

fun View.longSnackbar(@StringRes id: Int) {
    Snackbar.make(this, id, Snackbar.LENGTH_SHORT).show()
}
