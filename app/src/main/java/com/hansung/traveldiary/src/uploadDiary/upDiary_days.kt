package com.hansung.traveldiary.src.uploadDiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.maketravel.databinding.FrgmentDaysBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Day1: Fragment(){
    private val bgColors: MutableList<Int> = mutableListOf(
        android.R.color.white,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light
    )
    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter(bgColors as ArrayList<Int>)
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.uploadViewPager)
        return view
    }
}

class Day2: Fragment(){
    private val bgColors: MutableList<Int> = mutableListOf(
        android.R.color.white,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light
    )
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter(bgColors as ArrayList<Int>)
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        return view
    }
}
class Day3: Fragment(){
    private val bgColors: MutableList<Int> = mutableListOf(
        android.R.color.white,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light
    )
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter(bgColors as ArrayList<Int>)
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        return view
    }
}
class Day4: Fragment(){
    private val bgColors: MutableList<Int> = mutableListOf(
        android.R.color.white,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light
    )
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter(bgColors as ArrayList<Int>)
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        return view
    }
}
class Day5: Fragment(){
    private val bgColors: MutableList<Int> = mutableListOf(
        android.R.color.white,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light
    )
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter(bgColors as ArrayList<Int>)
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        return view
    }
}
class Day6: Fragment(){
    private val bgColors: MutableList<Int> = mutableListOf(
        android.R.color.white,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light
    )
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter(bgColors as ArrayList<Int>)
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        return view
    }
}
class Day7: Fragment(){
    private val bgColors: MutableList<Int> = mutableListOf(
        android.R.color.white,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light
    )
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter(bgColors as ArrayList<Int>)
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        return view
    }
}
