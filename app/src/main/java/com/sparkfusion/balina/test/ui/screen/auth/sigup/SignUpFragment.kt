package com.sparkfusion.balina.test.ui.screen.auth.sigup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.databinding.FragmentSignupBinding
import com.sparkfusion.balina.test.ui.exception.ViewBindingIsNullException
import com.sparkfusion.balina.test.ui.utils.shortSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _viewBinding: FragmentSignupBinding? = null
    private val viewBinding: FragmentSignupBinding
        get() = checkNotNull(_viewBinding) {
            throw ViewBindingIsNullException()
        }

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentSignupBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonRegister.setOnClickListener { onRegisterButtonClickListener() }

        handleRegistrationState()
    }

    private fun onRegisterButtonClickListener() {
        val password = viewBinding.editTextPassword.text.toString()
        val confirmPassword = viewBinding.editTextConfirmPassword.text.toString()
        if (password != confirmPassword) {
            viewBinding.editTextConfirmPassword.error =
                resources.getString(R.string.passwords_do_not_match)
            return
        }

        viewModel.handleIntent(
            SignUpIntent.RegisterAccount(
                viewBinding.editTextLogin.text.toString(),
                viewBinding.editTextPassword.text.toString()
            )
        )
    }

    private fun handleRegistrationState() {
        viewModel.registrationState.observe(viewLifecycleOwner) {
            when (it) {
                RegistrationState.IncorrectUsername -> {
                    viewBinding.editTextLogin.error =
                        resources.getString(R.string.should_not_be_empty)
                }

                RegistrationState.IncorrectPassword -> {
                    viewBinding.editTextPassword.error =
                        resources.getString(R.string.password_is_too_short)
                }

                RegistrationState.Empty -> {}
                RegistrationState.Loading -> {}
                RegistrationState.Success -> {
                    val navController = findNavController()
                    navController.popBackStack()
                    navController.navigate(R.id.nav_gallery)
                }

                RegistrationState.Error -> {
                    viewBinding.root.shortSnackbar(R.string.error)
                }
            }

            viewBinding.progressBar.isVisible = RegistrationState.Loading == it
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}