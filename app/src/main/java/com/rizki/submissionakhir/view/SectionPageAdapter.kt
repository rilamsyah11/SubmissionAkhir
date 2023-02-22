package com.rizki.submissionakhir.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPageAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    lateinit var Username: String

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentFollowers.slideInstance(Username)
            1 -> fragment = FragmentFollowing.slideInstance(Username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}