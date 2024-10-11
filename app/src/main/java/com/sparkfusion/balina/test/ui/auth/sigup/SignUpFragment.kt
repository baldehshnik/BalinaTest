package com.sparkfusion.balina.test.ui.auth.sigup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sparkfusion.balina.test.databinding.FragmentSignupBinding
import com.sparkfusion.balina.test.ui.exception.ViewBindingIsNullException

class SignUpFragment : Fragment() {

    private var _viewBinding: FragmentSignupBinding? = null
    private val viewBinding: FragmentSignupBinding
        get() = checkNotNull(_viewBinding) {
            throw ViewBindingIsNullException()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewBinding = FragmentSignupBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.buttonRegister.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}