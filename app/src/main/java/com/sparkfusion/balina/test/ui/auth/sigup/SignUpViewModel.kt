package com.sparkfusion.balina.test.ui.auth.sigup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sparkfusion.balina.test.domain.model.user.UserModel
import com.sparkfusion.balina.test.domain.usecase.RegisterUseCase
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
class SignUpViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: MainCoroutineDispatcher,
    private val signUpUseCase: RegisterUseCase
) : CommonViewModel<SignUpIntent>() {

    private val _registrationState = MutableLiveData<RegistrationState>(RegistrationState.Empty)
    val registrationState: LiveData<RegistrationState> get() = _registrationState

    override fun handleIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.RegisterAccount -> signUp(intent.username, intent.password)
        }
    }

    private fun signUp(username: String, password: String) {
        _registrationState.value = RegistrationState.Loading
        viewModelScope.launch(ioDispatcher) {
            if (username.isEmpty()) {
                withContext(mainDispatcher) {
                    _registrationState.value = RegistrationState.IncorrectUsername
                }
                return@launch
            }

            if (password.length < 8) {
                withContext(mainDispatcher) {
                    _registrationState.value = RegistrationState.IncorrectPassword
                }
                return@launch
            }

            signUpUseCase.invoke(UserModel(username, password))
                .onSuccess {
                    withContext(mainDispatcher) {
                        _registrationState.value = RegistrationState.Success
                    }
                }
                .onFailure {
                    withContext(mainDispatcher) {
                        _registrationState.value = RegistrationState.Error
                    }
                }
        }
    }
}

























