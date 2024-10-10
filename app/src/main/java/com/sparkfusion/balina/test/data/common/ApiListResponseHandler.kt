package com.sparkfusion.balina.test.data.common

import com.sparkfusion.balina.test.utils.common.Answer
import com.sparkfusion.balina.test.utils.exception.BalinaException
import com.sparkfusion.balina.test.utils.exception.UnexpectedException
import retrofit2.Response

class ApiListResponseHandler<R>(
    private val response: Response<List<R>>,
    private val handleExceptionCode: (code: Int) -> BalinaException
) {

    fun handleFetchedData(): Answer<List<R>> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) Answer.Failure(UnexpectedException())
            else Answer.Success(body)
        } else {
            Answer.Failure(handleExceptionCode(response.code()))
        }
    }
}
