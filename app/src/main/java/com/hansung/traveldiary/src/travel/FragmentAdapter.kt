package com.hansung.traveldiary.src.travel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        val fragment =  when(position)
        {
            0-> LasttripFragment().newInstant()
            1-> TriptogoFragment().newInstant()
            else -> LasttripFragment().newInstant()
        }
        return fragment
    }
    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position)
        {
            0->"가려는 여행"
            1->"다녀온 여행"
            else -> "main"
        }
        return title
    }

}