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
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.FragmentMakeDiaryDaySectionBinding
import com.hansung.traveldiary.src.*
import com.hansung.traveldiary.src.MainActivity.Companion.allDiaryList
import com.hansung.traveldiary.src.MainActivity.Companion.myDiaryList
import com.hansung.traveldiary.src.plan.ScheduleFragment
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import com.hansung.traveldiary.src.plan.TravelPlanMapFragment
import com.hansung.traveldiary.src.plan.diary
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel

class WriteDayDiaryFragment(val diaryInfo: DiaryInfo, val index: Int, val size: Int,val title:String) : Fragment() {
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
        user = Firebase.auth.currentUser
        db = Firebase.firestore


        transaction = childFragmentManager.beginTransaction()

        binding.uploadContents.setText(diaryInfo.diaryContents)

        //      btmSheetFragment=SelectDayBtmSheetFragment(size)
        val dayText = "${viewModel.dayData.value}일차 일기"
        println("dayText: ${dayText}")
        binding.atpTvDays.text = dayText

        binding.daySelectLayout.setOnClickListener{
            btmSheetFragment.show(childFragmentManager,btmSheetFragment.tag)
        }

        binding.uploadDiaryAddbtn.setOnClickListener {
            var intent = Intent(context, DiaryImageEditActivity::class.java)
            startActivity(intent)
        }
        //diaryInfo.diaryTitle=title.toString()
        val diaryInfoFolder= DiaryInfoFolder()
        var diaryDayList : ArrayList<DiaryDayInfo> = ArrayList<DiaryDayInfo>()
        db!!.collection("User").document("UserData").collection(user!!.email.toString()).document("Diary")
            .collection(MainActivity.myDiaryList[index].planTitle.toString())
            .document("DiaryData").get().addOnSuccessListener{ result ->
                val data = result.toObject<DiaryInfoFolder>()
                if(data != null){
                    diaryDayList=data.diaryDayList
                }
            }


        var diaryDayInfo=DiaryDayInfo()
        binding.uploadDiaryCommitbtn.setOnClickListener {
            println(MainActivity.myDiaryList[0].planTitle.toString())
            //다이어리 값 넣기
            diaryInfo.imagePathArray.add("https://firebasestorage.googleapis.com/v0/b/traveldiaryapp-2b2bc.appspot.com/o/profileImage%2Ftest%40test.com%2FprofileImage.png?alt=media&token=f0da3a26-a721-4fe5-a1ae-c48cf1a6be74")
            diaryInfo.imagePathArray=diaryInfo.imagePathArray
            diaryInfo.diaryContents=binding.uploadContents.text.toString()

            diaryInfo.diaryTitle=title
            //var diaryDayList : ArrayList<DiaryDayInfo> = ArrayList()
            diaryDayInfo.date="123123"
            diaryDayInfo.diaryInfo=diaryInfo
            //diaryDayList=ArrayList()
            //기존다이어리 인덱스에 넣는 작업

            diaryDayList[viewModel.dayData.value!! -1]=diaryDayInfo

            println(viewModel.dayData.value!! -1)
            db!!.collection("User").document("UserData").collection(user!!.email.toString()).document("Diary")
                .collection(MainActivity.myDiaryList[index].planTitle.toString())
                .document("DiaryData").set(DiaryInfoFolder(
                    diaryDayList
                )
            )

        }
        return binding.root
    }


}