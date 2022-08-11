package com.example.mainactivity.startingactivities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mainactivity.LoginFragment

class PageAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {



    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        when(position)
        {

            0 -> {
                return LoginFragment()
            }

            1->
            {
                return  RegisterFragment()


            }

            else ->
            {
                return LoginFragment()
            }

        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Login"
            }
            1 -> {
                return "Register"

            }

        }
        return super.getPageTitle(position)
    }



}