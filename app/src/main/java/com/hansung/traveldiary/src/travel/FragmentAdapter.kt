package com.hansung.traveldiary.src.travel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        val fragment =  when(position)
        {
            0-> TravelPlanSectionFragment().newInstant()
            1-> TravelDiarySectionFragment().newInstant()
            else -> TravelPlanSectionFragment().newInstant()
        }
        return fragment
    }
    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position)
        {
            0->"여행 계획"
            1->"다이어리"
            else -> "main"
        }
        return title
    }

}