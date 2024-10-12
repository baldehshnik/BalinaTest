package com.sparkfusion.balina.test.ui.screen.auth.signin

sealed interface LoginState {
    data object Empty : LoginState
    data object Loading : LoginState
    data object Error : LoginState
    data object Success : LoginState
    data object IncorrectUsername : LoginState
    data object IncorrectPassword : LoginState
}
