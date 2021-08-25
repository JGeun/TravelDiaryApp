package com.hansung.traveldiary.src.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hansung.traveldiary.src.bulletin.ContentFragment
import com.hansung.traveldiary.src.bulletin.InstargramFragment
import com.hansung.traveldiary.src.bulletin.UserNameFragment
import com.hansung.traveldiary.src.home.HistoryPlaceFragment
import com.hansung.traveldiary.src.home.NaturePlaceFragment
import com.hansung.traveldiary.src.home.PopularPlaceFragment
import com.hansung.traveldiary.src.home.RecommandActivity

class RecommandViewPagerAdapter (fragment: FragmentActivity): FragmentStateAdapter( fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PopularPlaceFragment()

            }
            1 -> {
                NaturePlaceFragment()
            }
            else -> {
                HistoryPlaceFragment()
            }
        }
    }
}