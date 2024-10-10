package com.sparkfusion.balina.test.domain.mapper.user

import com.sparkfusion.balina.test.data.entity.user.UserDataEntity
import com.sparkfusion.balina.test.domain.model.user.UserModel
import com.sparkfusion.balina.test.utils.MapFactory
import javax.inject.Inject

class UserFactory @Inject constructor() : MapFactory<UserDataEntity, UserModel> {

    override fun mapTo(input: UserDataEntity): UserModel = with(input) {
        UserModel(login, password)
    }

    override fun mapFrom(input: UserModel): UserDataEntity = with(input) {
        UserDataEntity(login, password)
    }
}
