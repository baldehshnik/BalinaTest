package com.sparkfusion.balina.test.ui.auth

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sparkfusion.balina.test.ui.auth.signin.SignInFragment
import com.sparkfusion.balina.test.ui.auth.sigup.SignUpFragment

class AuthPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SignInFragment()
            1 -> SignUpFragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}