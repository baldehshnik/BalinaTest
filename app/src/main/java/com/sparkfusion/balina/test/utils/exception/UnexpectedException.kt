package com.sparkfusion.balina.test.utils.exception

class UnexpectedException(
    cause: Throwable? = null
) : BalinaException(cause?.message ?: "Occurred unknown exception", cause)