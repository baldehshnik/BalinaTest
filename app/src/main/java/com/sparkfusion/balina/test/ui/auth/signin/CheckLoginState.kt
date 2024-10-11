package com.sparkfusion.balina.test.ui.auth.signin

sealed interface CheckLoginState {
    data object Loading : CheckLoginState
    data object Success : CheckLoginState
    data object Error : CheckLoginState
    data object Failure : CheckLoginState
}
