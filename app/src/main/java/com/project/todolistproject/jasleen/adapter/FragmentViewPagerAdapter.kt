package com.project.todolistproject.jasleen.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.project.todolistproject.jasleen.fragments.CalenderFragment
import com.project.todolistproject.jasleen.fragments.HomeFragment

/**
 * Created by Jasleen Kaur on 23-02-2021.
 */
class FragmentViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                CalenderFragment()
            }
            else -> {
                HomeFragment()
            }
        }
    }


}