package com.hansung.traveldiary.src.diary.write_diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.hansung.traveldiary.databinding.FragmentMakeDiaryDaySectionBinding
import com.hansung.traveldiary.src.DiaryData
import com.hansung.traveldiary.src.plan.TravelPlanMapFragment
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel

class WriteDayDiaryFragment(val diaryInfo: DiaryData, val index: Int, val size: Int) : Fragment() {
    private lateinit var btmSheetFragment :SelectDayBtmSheetFragment
    private val viewModel : SelectDayViewModel by activityViewModels()
    private var user: FirebaseUser? = null
    private var db: FirebaseFirestore? = null
    private val userPlanDataModel : SharedPlaceViewModel by viewModels()
    private lateinit var transaction : FragmentTransaction
    private lateinit var travelPlanMapFragment : TravelPlanMapFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMakeDiaryDaySectionBinding.inflate(layoutInflater)

        Log.d("과정", "WriteAdapter-ViewModel Data: ${viewModel.dayData.value!!}")
        println("fragment 시작")

        if(diaryInfo.imagePathArray.size == 0){
            binding.uploadViewPager.isVisible = false
            binding.indicator.isVisible = false
        }else{
            binding.uploadViewPager.isVisible = true
            binding.indicator.isVisible = true
            binding.uploadViewPager.adapter =
                WriteImageAdapter(diaryInfo.imagePathArray)
            binding.uploadViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            //인디케이터
            binding.indicator.setViewPager(binding.uploadViewPager)
        }

        transaction = childFragmentManager.beginTransaction()

        binding.uploadContents.setText(diaryInfo.diaryContents)

        btmSheetFragment=SelectDayBtmSheetFragment(size)
        val dayText = "${viewModel.dayData.value}일차 일기"
        println("dayText: ${dayText}")
        binding.atpTvDays.text = dayText

        binding.daySelectLayout.setOnClickListener{
            btmSheetFragment.show(childFragmentManager,btmSheetFragment.tag)
        }

        binding.uploadDiaryCommitbtn.setOnClickListener {
            var intent = Intent(context, DiaryImageEditActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }


}