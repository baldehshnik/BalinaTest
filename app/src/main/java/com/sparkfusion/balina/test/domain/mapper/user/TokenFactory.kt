package com.sparkfusion.balina.test.domain.mapper.user

import com.sparkfusion.balina.test.data.entity.user.TokenDataEntity
import com.sparkfusion.balina.test.domain.model.user.TokenModel
import com.sparkfusion.balina.test.utils.MapFactory
import javax.inject.Inject

class TokenFactory @Inject constructor() : MapFactory<TokenDataEntity, TokenModel> {

    override fun mapTo(input: TokenDataEntity): TokenModel = with(input) {
        TokenModel(userId, login, token)
    }

    override fun mapFrom(input: TokenModel): TokenDataEntity = with(input) {
        TokenDataEntity(userId, login, token)
    }
}
