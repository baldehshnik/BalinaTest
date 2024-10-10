package com.sparkfusion.balina.test.utils.exception

class NetworkException(cause: Throwable) : BalinaException(cause.message, cause)