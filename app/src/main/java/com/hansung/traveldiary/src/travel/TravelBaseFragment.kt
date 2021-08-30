package com.hansung.traveldiary.src.travel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.hansung.traveldiary.databinding.FragmentTravelBinding
import com.hansung.traveldiary.src.bulletin.searchActivity

class TravelBaseFragment : Fragment(){
    private lateinit var binding : FragmentTravelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTravelBinding.inflate(inflater, container, false)

        val pagerAdapter = FragmentAdapter(childFragmentManager)
        val pager = binding.viewPager
        pager.adapter = pagerAdapter
        val tab = binding.tab
        tab.setupWithViewPager(pager)
        return binding.root
    }
}