package com.hansung.traveldiary.src.bulletin

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class SearchViewPagerAdapter(fragment: FragmentActivity): FragmentStateAdapter( fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                InstargramFragment()

            }
            1->{
                ContentFragment()
            }
            else->{

                UserNameFragment()
            }
        }
    }


}
