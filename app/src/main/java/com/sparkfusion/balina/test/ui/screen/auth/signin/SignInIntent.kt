package com.sparkfusion.balina.test.ui.screen.auth.signin

import com.sparkfusion.balina.test.utils.common.UserIntent

sealed interface SignInIntent : UserIntent {

    data class Login(val username: String, val password: String) : SignInIntent
    data object CheckUserLogin : SignInIntent
}
