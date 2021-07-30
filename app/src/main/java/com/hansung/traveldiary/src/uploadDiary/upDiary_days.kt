package com.hansung.traveldiary.src.uploadDiary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FrgmentDaysBinding

class Day1: Fragment(){
    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter()
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        //인디케이터
        binding.indicator.setViewPager(binding.uploadViewPager)
        return view
    }
}

class Day2: Fragment(){
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter()
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.uploadViewPager)
        return view
    }
}
class Day3: Fragment(){
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter()
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.uploadViewPager)
        return view
    }
}
class Day4: Fragment(){

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter()
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.uploadViewPager)
        return view
    }
}
class Day5: Fragment(){
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.frgment_days,container,false)
        val binding= FrgmentDaysBinding.bind(view)
        binding.uploadViewPager.adapter=ViewPagerAdapter()
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.uploadViewPager)
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
        binding.uploadViewPager.adapter=ViewPagerAdapter()
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.uploadViewPager)
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
        binding.uploadViewPager.adapter=ViewPagerAdapter()
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.indicator.setViewPager(binding.uploadViewPager)
        return view
    }
}
