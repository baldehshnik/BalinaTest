package com.sparkfusion.balina.test.ui.screen.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sparkfusion.balina.test.domain.model.user.UserModel
import com.sparkfusion.balina.test.domain.usecase.login.CheckUserLoginUseCase
import com.sparkfusion.balina.test.domain.usecase.login.LoginUseCase
import com.sparkfusion.balina.test.ui.utils.withMainContext
import com.sparkfusion.balina.test.utils.common.CommonViewModel
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val signInUseCase: LoginUseCase,
    private val checkUserLoginUseCase: CheckUserLoginUseCase
) : CommonViewModel<SignInIntent>() {

    private val _loginState = MutableLiveData<LoginState>(LoginState.Empty)
    val loginState: LiveData<LoginState> get() = _loginState

    private val _checkLoginState = MutableLiveData<CheckLoginState>(CheckLoginState.Loading)
    val checkLoginState: LiveData<CheckLoginState> get() = _checkLoginState

    override fun handleIntent(intent: SignInIntent) {
        when (intent) {
            is SignInIntent.Login -> singIn(intent.username, intent.password)
            SignInIntent.CheckUserLogin -> checkUserLogin()
        }
    }

    private fun singIn(username: String, password: String) {
        _loginState.value = LoginState.Loading
        viewModelScope.launch(ioDispatcher) {
            if (username.isEmpty()) {
                withMainContext(_loginState, LoginState.IncorrectUsername)
                return@launch
            }

            if (password.length < 8) {
                withMainContext(_loginState, LoginState.IncorrectPassword)
                return@launch
            }

            signInUseCase.invoke(UserModel(username, password))
                .onSuccess {
                    withMainContext(_loginState, LoginState.Success)
                }
                .onFailure {
                    withMainContext(_loginState, LoginState.Error)
                }
        }
    }

    private fun checkUserLogin() {
        viewModelScope.launch(ioDispatcher) {
            checkUserLoginUseCase.invoke()
                .onSuccess {
                    withMainContext(
                        _checkLoginState,
                        if (it) CheckLoginState.Success else CheckLoginState.Failure
                    )
                }
                .onFailure {
                    withMainContext(_checkLoginState, CheckLoginState.Error)
                }
        }
    }

    init {
        handleIntent(SignInIntent.CheckUserLogin)
    }
}
