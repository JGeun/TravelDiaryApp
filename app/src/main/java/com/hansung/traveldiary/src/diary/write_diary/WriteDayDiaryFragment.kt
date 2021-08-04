package com.hansung.traveldiary.src.diary.write_diary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.hansung.traveldiary.databinding.FragmentMakeDiaryDaySectionBinding
import com.hansung.traveldiary.src.DiaryInfo
import com.hansung.traveldiary.src.DiaryInfoFolder

class WriteDayDiaryFragment(val diaryInfo: DiaryInfo, val index: Int, val size: Int) : Fragment() {
    private lateinit var btmSheetFragment :SelectDayBtmSheetFragment
    private val viewModel : SelectDayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMakeDiaryDaySectionBinding.inflate(layoutInflater)

        Log.d("과정", "WriteAdapter-ViewModel Data: ${viewModel.dayData.value!!}")
        println("fragment 시작")
        binding.uploadViewPager.adapter =
            WriteImageAdapter(diaryInfo.imagePathArray)
        binding.uploadViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //인디케이터
        binding.indicator.setViewPager(binding.uploadViewPager)

        binding.uploadContents.setText(diaryInfo.diaryContents)

        btmSheetFragment=SelectDayBtmSheetFragment(size)
        val dayText = "${viewModel.dayData.value}일차 일기"
        println("dayText: ${dayText}")
        binding.atpTvDays.text = dayText

        binding.daySelectLayout.setOnClickListener{
            btmSheetFragment.show(childFragmentManager,btmSheetFragment.tag)
        }

        return binding.root
    }

}