package com.sparkfusion.balina.test.utils.exception

open class BalinaException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message ?: "Default application exception", cause)