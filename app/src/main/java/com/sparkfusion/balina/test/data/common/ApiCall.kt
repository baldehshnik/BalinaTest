package com.sparkfusion.balina.test.data.common

import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.exception.BadRequestException
import com.sparkfusion.balina.test.utils.exception.BalinaException
import com.sparkfusion.balina.test.utils.exception.IncorrectSignInException
import com.sparkfusion.balina.test.utils.exception.LoginAlreadyExistsException
import com.sparkfusion.balina.test.utils.exception.NetworkException
import com.sparkfusion.balina.test.utils.exception.ServerException
import com.sparkfusion.balina.test.utils.exception.UnexpectedException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    call: suspend () -> Answer<T>
): Answer<T> = withContext(dispatcher) {
    try {
        call.invoke()
    } catch (exception: Exception) {
        handleApiException(exception)
    }
}

fun handleSignInExceptionCode(code: Int): BalinaException {
    return when (code) {
        400 -> IncorrectSignInException()
        else -> UnexpectedException()
    }
}

fun handleSignUpExceptionCode(code: Int): BalinaException {
    return when (code) {
        400 -> LoginAlreadyExistsException()
        else -> UnexpectedException()
    }
}

fun commonHandleExceptionCode(code: Int): BalinaException {
    return when (code) {
        in 400..499 -> BadRequestException()
        in 500..599 -> ServerException()
        else -> UnexpectedException()
    }
}

private fun handleApiException(exception: Exception): Answer.Failure {
    return Answer.Failure(
        when (exception) {
            is IOException -> NetworkException(exception)
            else -> UnexpectedException(exception)
        }
    )
}
