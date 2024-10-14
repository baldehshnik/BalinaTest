package com.sparkfusion.balina.test.ui.screen.auth.sigup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sparkfusion.balina.test.domain.model.user.UserModel
import com.sparkfusion.balina.test.domain.usecase.register.RegisterUseCase
import com.sparkfusion.balina.test.ui.utils.withMainContext
import com.sparkfusion.balina.test.utils.common.CommonViewModel
import com.sparkfusion.balina.test.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
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
                withMainContext(_registrationState, RegistrationState.IncorrectUsername)
                return@launch
            }

            if (password.length < 8) {
                withMainContext(_registrationState, RegistrationState.IncorrectPassword)
                return@launch
            }

            signUpUseCase.invoke(UserModel(username, password))
                .onSuccess {
                    withMainContext(_registrationState, RegistrationState.Success)
                }
                .onFailure {
                    withMainContext(_registrationState, RegistrationState.Error)
                }
        }
    }
}
