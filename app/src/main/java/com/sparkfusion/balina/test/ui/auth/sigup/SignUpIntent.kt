package com.sparkfusion.balina.test.ui.auth.sigup

import com.sparkfusion.balina.test.utils.common.UserIntent

sealed interface SignUpIntent : UserIntent {

    data class RegisterAccount(val username: String, val password: String) : SignUpIntent
}
