package com.sparkfusion.balina.test.ui.auth.sigup

sealed interface RegistrationState {
    data object IncorrectUsername : RegistrationState
    data object IncorrectPassword : RegistrationState
    data object Error : RegistrationState
    data object Success : RegistrationState
    data object Empty : RegistrationState
    data object Loading: RegistrationState
}
