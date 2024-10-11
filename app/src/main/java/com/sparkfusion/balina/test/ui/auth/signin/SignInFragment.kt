package com.sparkfusion.balina.test.ui.auth.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sparkfusion.balina.test.databinding.FragmentSigninBinding
import com.sparkfusion.balina.test.ui.exception.ViewBindingIsNullException

class SignInFragment : Fragment() {

    private var _viewBinding: FragmentSigninBinding? = null
    private val viewBinding: FragmentSigninBinding
        get() = checkNotNull(_viewBinding) {
        throw ViewBindingIsNullException()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentSigninBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonLogin.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}