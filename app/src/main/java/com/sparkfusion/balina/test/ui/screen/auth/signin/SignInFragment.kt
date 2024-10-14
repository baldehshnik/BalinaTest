package com.sparkfusion.balina.test.ui.screen.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.databinding.FragmentSigninBinding
import com.sparkfusion.balina.test.ui.exception.ViewBindingIsNullException
import com.sparkfusion.balina.test.ui.utils.shortSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _viewBinding: FragmentSigninBinding? = null
    private val viewBinding: FragmentSigninBinding
        get() = checkNotNull(_viewBinding) {
            throw ViewBindingIsNullException()
        }

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentSigninBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonLogin.setOnClickListener { onLoginButtonClickListener() }

        viewModel.checkLoginState.observe(viewLifecycleOwner, ::handleCheckLoginState)
        viewModel.loginState.observe(viewLifecycleOwner, ::handleLoginState)
    }

    private fun onLoginButtonClickListener() {
        viewModel.handleIntent(
            SignInIntent.Login(
                viewBinding.editTextLogin.text.toString(),
                viewBinding.editTextPassword.text.toString()
            )
        )
    }

    private fun handleCheckLoginState(checkLoginState: CheckLoginState) {
        when (checkLoginState) {
            CheckLoginState.Loading -> {
                viewBinding.progressBar.isVisible = true
                viewBinding.buttonLogin.isVisible = false
                viewBinding.registrationText.isVisible = false
                viewBinding.editTextLoginLayout.isVisible = false
                viewBinding.editTextPasswordLayout.isVisible = false
            }

            CheckLoginState.Success -> {
                findNavController().navigate(R.id.nav_gallery)
            }

            CheckLoginState.Failure -> {
                viewBinding.progressBar.isVisible = false
                viewBinding.buttonLogin.isVisible = true
                viewBinding.registrationText.isVisible = true
                viewBinding.editTextLoginLayout.isVisible = true
                viewBinding.editTextPasswordLayout.isVisible = true
            }

            CheckLoginState.Error -> {
                viewBinding.progressBar.isVisible = false
                viewBinding.buttonLogin.isVisible = true
                viewBinding.registrationText.isVisible = true
                viewBinding.editTextLoginLayout.isVisible = true
                viewBinding.editTextPasswordLayout.isVisible = true

                viewBinding.root.shortSnackbar(R.string.error)
            }
        }
    }

    private fun handleLoginState(loginState: LoginState) {
        when (loginState) {
            LoginState.Empty -> {}
            LoginState.Loading -> {}
            LoginState.Success -> {
                val navController = findNavController()
                navController.popBackStack()
                navController.navigate(R.id.nav_gallery)
            }

            LoginState.Error -> {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.error),
                    Toast.LENGTH_SHORT
                ).show()
            }

            LoginState.IncorrectUsername -> {
                viewBinding.editTextLogin.error = resources.getString(R.string.should_not_be_empty)
            }

            LoginState.IncorrectPassword -> {
                viewBinding.editTextPassword.error =
                    resources.getString(R.string.password_is_too_short)
            }
        }

        viewBinding.progressBar.isVisible = LoginState.Loading == loginState
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}