package com.sparkfusion.balina.test.ui.auth.signin

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

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val fab = requireActivity().findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab)

        toolbar?.visibility = View.GONE
        fab?.visibility = View.GONE

        viewBinding.buttonLogin.setOnClickListener {
            viewModel.handleIntent(
                SignInIntent.Login(
                    viewBinding.editTextLogin.text.toString(),
                    viewBinding.editTextPassword.text.toString()
                )
            )
        }

        viewModel.checkLoginState.observe(viewLifecycleOwner) {
            when (it) {
                CheckLoginState.Loading -> {
                    viewBinding.progressBar.isVisible = true
                    viewBinding.buttonLogin.isVisible = false
                    viewBinding.registrationText.isVisible = false
                    viewBinding.editTextLoginLayout.isVisible = false
                    viewBinding.editTextPasswordLayout.isVisible = false
                }

                CheckLoginState.Success -> {
                    toolbar?.visibility = View.VISIBLE
                    fab?.visibility = View.VISIBLE

                    findNavController().navigate(R.id.nav_gallery)
                }

                CheckLoginState.Failure -> {
                    viewBinding.progressBar.isVisible = false
                    viewBinding.buttonLogin.isVisible = true
                    viewBinding.registrationText.isVisible = true
                    viewBinding.editTextLoginLayout.isVisible = true
                    viewBinding.editTextPasswordLayout.isVisible = true
                    toolbar?.visibility = View.VISIBLE
                    fab?.visibility = View.VISIBLE
                }

                CheckLoginState.Error -> {
                    viewBinding.progressBar.isVisible = false
                    viewBinding.buttonLogin.isVisible = true
                    viewBinding.registrationText.isVisible = true
                    viewBinding.editTextLoginLayout.isVisible = true
                    viewBinding.editTextPasswordLayout.isVisible = true
                    toolbar?.visibility = View.VISIBLE
                    fab?.visibility = View.VISIBLE

                    Toast.makeText(requireContext(), resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            }
        }


        viewModel.loginState.observe(viewLifecycleOwner) {
            when (it) {
                LoginState.Empty -> {}
                LoginState.Loading -> {}
                LoginState.Success -> {
                    val navController = findNavController()
                    navController.popBackStack()
                    navController.navigate(R.id.nav_gallery)
                }
                LoginState.Error -> {
                    Toast.makeText(requireContext(), resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
                LoginState.IncorrectUsername -> {
                    viewBinding.editTextLogin.error = resources.getString(R.string.should_not_be_empty)
                }
                LoginState.IncorrectPassword -> {
                    viewBinding.editTextPassword.error = resources.getString(R.string.password_is_too_short)
                }
            }

            viewBinding.progressBar.isVisible = LoginState.Loading == it
        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}