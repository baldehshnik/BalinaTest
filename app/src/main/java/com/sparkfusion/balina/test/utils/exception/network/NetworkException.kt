package com.sparkfusion.balina.test.utils.exception.network

import com.sparkfusion.balina.test.utils.exception.common.BalinaException

class NetworkException(cause: Throwable) : BalinaException(cause.message, cause)