package com.sparkfusion.balina.test.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sparkfusion.balina.test.R
import com.sparkfusion.balina.test.databinding.FragmentAuthBinding
import com.sparkfusion.balina.test.ui.exception.ViewBindingIsNullException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var _viewBinding: FragmentAuthBinding? = null
    private val viewBinding: FragmentAuthBinding
        get() = checkNotNull(_viewBinding) {
            throw ViewBindingIsNullException()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentAuthBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = viewBinding.viewPager
        val tabLayout = viewBinding.tabLayout

        viewPager.adapter = AuthPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.login)
                1 -> tab.text = resources.getString(R.string.registration)
            }
        }.attach()
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
