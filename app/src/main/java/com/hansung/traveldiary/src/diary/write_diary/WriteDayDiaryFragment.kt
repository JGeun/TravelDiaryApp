package com.hansung.traveldiary.src.diary.write_diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentMakeDiaryDaySectionBinding
import com.hansung.traveldiary.src.DiaryInfo
import com.hansung.traveldiary.src.DiaryInfoFolder
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.PlaceInfoFolder
import com.hansung.traveldiary.src.plan.ScheduleFragment
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import com.hansung.traveldiary.src.plan.TravelPlanMapFragment
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel

class WriteDayDiaryFragment(val diaryInfo: DiaryInfo, val index: Int, val size: Int) : Fragment() {
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
            binding.uploadDiaryCommitbtn.setText("사진 추가")
        }else{
            binding.uploadViewPager.isVisible = true
            binding.indicator.isVisible = true
            binding.uploadViewPager.adapter =
                WriteImageAdapter(diaryInfo.imagePathArray)
            binding.uploadViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            //인디케이터
            binding.indicator.setViewPager(binding.uploadViewPager)
            binding.uploadDiaryCommitbtn.setText("사진 편집")
        }

        val title = MainActivity.myDiaryList[index].planTitle
        travelPlanMapFragment = TravelPlanMapFragment(title)

        user = Firebase.auth.currentUser
        db = Firebase.firestore

        initViewModel(title!!)

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

        binding.ivShowPlacelist.setOnClickListener {
            (activity as WriteDiaryActivity).showMap(ScheduleFragment())
        }

        return binding.root
    }

    fun initViewModel(title: String){
        val userDocRef = db!!.collection("User").document("UserData")
        userDocRef.collection(user!!.email.toString()).document("Plan").collection(title).document("PlaceInfo")
            .get().addOnSuccessListener  { documentSnapshot ->
                var placeInfoFolder = documentSnapshot.toObject<PlaceInfoFolder>()!!
                userPlanDataModel.putAllData(placeInfoFolder)
            }

    }

}