package com.sparkfusion.balina.test.ui.window.activity

sealed interface LoadUsernameState {
    data class Success(val username: String) : LoadUsernameState
    data object Failure : LoadUsernameState
}
