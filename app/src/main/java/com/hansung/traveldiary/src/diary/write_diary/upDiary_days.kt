package com.hansung.traveldiary.src.diary.write_diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.hansung.traveldiary.databinding.FragmentMakeDiaryDaySectionBinding
import com.hansung.traveldiary.src.DiaryInfoFolder

class WriteDayDiaryFragment(val diaryInfoFolder : DiaryInfoFolder, val day: Int): Fragment(){
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        val binding= FragmentMakeDiaryDaySectionBinding.inflate(layoutInflater)

        binding.uploadViewPager.adapter= WriteImageAdapter(diaryInfoFolder.diaryDayList[day].diaryInfo.imagePathArray)
        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL

        //인디케이터
        binding.indicator.setViewPager(binding.uploadViewPager)
        binding.writeTitle.setText(diaryInfoFolder.diaryDayList[day].diaryInfo.diaryTitle)
        binding.upDiaryText.setText(diaryInfoFolder.diaryDayList[day].diaryInfo.diaryContents)

        return binding.root
    }
}
//
//class Day1: Fragment(){
//    override fun onCreateView(inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        println(1)
//        val view=inflater.inflate(R.layout.fragment_make_diary_day_section,container,false)
//        val binding= FragmentMakeDiaryDaySectionBinding.bind(view)
//        binding.uploadViewPager.adapter= ViewPagerAdapter()
//        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
//        //인디케이터
//        binding.indicator.setViewPager(binding.uploadViewPager)
//        return view
//    }
//}
//
//class Day2: Fragment(){
//    override fun onCreateView(inflater: LayoutInflater,
//                              container: ViewGroup?,
//                              savedInstanceState: Bundle?
//    ): View? {
//        println(2)
//        val view=inflater.inflate(R.layout.fragment_make_diary_day_section,container,false)
//        val binding= FragmentMakeDiaryDaySectionBinding.bind(view)
//        binding.uploadViewPager.adapter= ViewPagerAdapter()
//        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
//        binding.indicator.setViewPager(binding.uploadViewPager)
//        return view
//    }
//}
//class Day3: Fragment(){
//    override fun onCreateView(inflater: LayoutInflater,
//                              container: ViewGroup?,
//                              savedInstanceState: Bundle?
//    ): View? {
//        println(3)
//        val view=inflater.inflate(R.layout.fragment_make_diary_day_section,container,false)
//        val binding= FragmentMakeDiaryDaySectionBinding.bind(view)
//        binding.uploadViewPager.adapter= ViewPagerAdapter()
//        binding.uploadViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
//        binding.indicator.setViewPager(binding.uploadViewPager)
//        return view
//    }
//}
