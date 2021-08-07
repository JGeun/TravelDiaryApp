package com.hansung.traveldiary.src.diary.write_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.firebase.firestore.FirebaseFirestoreException
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityWriteDiaryBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.plan.ScheduleFragment
import com.hansung.traveldiary.util.StatusBarUtil

class WriteDiaryActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWriteDiaryBinding.inflate(layoutInflater)
    }

    private val viewModel : SelectDayViewModel by viewModels()
    private var index = 0
    private var day = 0

    companion object{
        val fragmentArray = ArrayList<WriteDayDiaryFragment>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        Log.d("과정", "WriteDiaryActivity Create")
        index = intent.getIntExtra("index", 0)
        day = intent.getIntExtra("day", 0)

        Log.d("과정", "Write-넘어온Index: $index")
        Log.d("과정", "Write-넘어온Day: $day")

        viewModel.dayData.observe(this){
            println("들어옴")
            val transaction2 = supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragmentArray[viewModel.dayData.value!!-1])
            transaction2.commit()
        }





        binding.writeDiaryTitle.setText(MainActivity.myDiaryList[index].diaryData.diaryBaseData.title)
        binding.writeDiaryIvCancle.setOnClickListener {
            finish()
        }
        binding.showPlacelist.setOnClickListener {
            var intent = Intent(it.context, ShowPlacelistActivity::class.java)
            intent.putExtra("index", index)
            startActivity(intent)
        }

    }
    override fun onStart() {
        super.onStart()
        fragmentArray.clear()
        Log.d("과정", "Write-Start Index: $index")
        Log.d("과정", "Write-Start Day: $day")
        for (i in 1..MainActivity.myDiaryList[index].diaryData.diaryInfoFolder.diaryDayList.size) {
            fragmentArray.add(WriteDayDiaryFragment(MainActivity.myDiaryList[index].diaryData.diaryInfoFolder.diaryDayList[i-1].diaryInfo, index, MainActivity.myDiaryList[index].diaryData.diaryInfoFolder.diaryDayList.size,binding.writeDiaryTitle.text.toString()))
        }

        viewModel.setDay(day)
        println("viewModelDay: ${viewModel.dayData.value}")
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragmentArray[viewModel.dayData.value!!-1])
        transaction.commit()
    }

}



