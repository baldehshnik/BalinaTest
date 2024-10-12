package com.sparkfusion.balina.test.ui.screen.auth.sigup

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
import com.sparkfusion.balina.test.databinding.FragmentSignupBinding
import com.sparkfusion.balina.test.ui.exception.ViewBindingIsNullException
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
        viewBinding.buttonRegister.setOnClickListener {
            val password = viewBinding.editTextPassword.text.toString()
            val confirmPassword = viewBinding.editTextConfirmPassword.text.toString()
            if (password != confirmPassword) {
                viewBinding.editTextConfirmPassword.error =
                    resources.getString(R.string.passwords_do_not_match)
                return@setOnClickListener
            }

            viewModel.handleIntent(
                SignUpIntent.RegisterAccount(
                    viewBinding.editTextLogin.text.toString(),
                    viewBinding.editTextPassword.text.toString()
                )
            )
        }

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
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
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