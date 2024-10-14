package com.sparkfusion.balina.test.utils.exception.common

open class BalinaException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message ?: "Default application exception", cause)