package com.sparkfusion.balina.test.domain.repository.local

import com.sparkfusion.balina.test.utils.common.Answer

interface ILocalCommentsRepository {

    suspend fun deleteComments(imageId: Int): Answer<Unit>
}