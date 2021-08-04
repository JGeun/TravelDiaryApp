package com.hansung.traveldiary.src.diary.write_diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.hansung.traveldiary.databinding.FragmentMakeDiaryDaySectionBinding
import com.hansung.traveldiary.src.DiaryInfoFolder

class WriteDayDiaryFragment(val diaryInfoFolder: DiaryInfoFolder, val day: Int, val index: Int) : Fragment() {
    private lateinit var btmSheetFragment:BottomSheetFragment
    private lateinit var viewModel:WriteViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMakeDiaryDaySectionBinding.inflate(layoutInflater)
        println("fragment 시작")
        viewModel =WriteViewModel()
        binding.uploadViewPager.adapter =
            WriteImageAdapter(diaryInfoFolder.diaryDayList[day].diaryInfo.imagePathArray)
        binding.uploadViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //인디케이터
        binding.indicator.setViewPager(binding.uploadViewPager)
 //       binding..setText(diaryInfoFolder.diaryDayList[day].diaryInfo.diaryTitle)
//        binding.upDiaryText.setText(diaryInfoFolder.diaryDayList[day].diaryInfo.diaryContents)

        btmSheetFragment=BottomSheetFragment()
        binding.atpTvDays.text="1일차 일기"

        binding.daySelectLayout.setOnClickListener{
            btmSheetFragment.show(childFragmentManager,"btmDialog")
        }
        viewModel.data.observe(viewLifecycleOwner){
            val bundle=Bundle()
            val data= viewModel.data.value?.plus(1)
            if (data != null) {
                bundle.putInt("day",data)
            }
            println("뷰모델 값 변경")
            binding.atpTvDays.setText("${data}일차 일기")
        }
        /*binding.writeDiarySpinner.adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, spinnerArray)
        binding.writeDiarySpinner.setSelection(viewModel.data.value!!)

        binding.writeDiarySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.setData(position)
//                    spinnerArray.clear()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    viewModel.setData(0)
                }
            }*/
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        println("stop")
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
