package com.sparkfusion.balina.test.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sparkfusion.balina.test.domain.model.user.UserModel
import com.sparkfusion.balina.test.domain.usecase.CheckUserLoginUseCase
import com.sparkfusion.balina.test.domain.usecase.LoginUseCase
import com.sparkfusion.balina.test.utils.common.CommonViewModel
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import com.sparkfusion.balina.test.utils.dispatchers.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: MainCoroutineDispatcher,
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
                withContext(mainDispatcher) {
                    _loginState.value = LoginState.IncorrectUsername
                }
                return@launch
            }

            if (password.length < 8) {
                withContext(mainDispatcher) {
                    _loginState.value = LoginState.IncorrectPassword
                }
                return@launch
            }

            signInUseCase.invoke(UserModel(username, password))
                .onSuccess {
                    withContext(mainDispatcher) {
                        _loginState.value = LoginState.Success
                    }
                }
                .onFailure {
                    withContext(mainDispatcher) {
                        _loginState.value = LoginState.Error
                    }
                }
        }
    }

    private fun checkUserLogin() {
        viewModelScope.launch(ioDispatcher) {
            checkUserLoginUseCase.invoke()
                .onSuccess {
                    withContext(mainDispatcher) {
                        _checkLoginState.value = if (it) CheckLoginState.Success else CheckLoginState.Failure
                    }
                }
                .onFailure {
                    withContext(mainDispatcher) {
                        _checkLoginState.value = CheckLoginState.Error
                    }
                }
        }
    }

    init {
        handleIntent(SignInIntent.CheckUserLogin)
    }
}
























