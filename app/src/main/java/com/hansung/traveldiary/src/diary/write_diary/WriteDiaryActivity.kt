package com.hansung.traveldiary.src.diary.write_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityWriteDiaryBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.util.StatusBarUtil

class WriteDiaryActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityWriteDiaryBinding.inflate(layoutInflater)
    }

    private val viewModel : SelectDayViewModel by viewModels()

    companion object{
        val fragmentArray = ArrayList<WriteDayDiaryFragment>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        val index = intent.getIntExtra("index", 0)
        val day = intent.getIntExtra("day", 0)
        //날짜 수에 따른 스피너 동적 생성

        for (i in 1..MainActivity.myDiaryList[index].diaryData.diaryInfoFolder.diaryDayList.size) {
            fragmentArray.add(WriteDayDiaryFragment(MainActivity.myDiaryList[index].diaryData.diaryInfoFolder, i-1, index))
        }

        println("onCreate")
        val transaction = supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragmentArray[viewModel.dayData.value!!])
        transaction.commit()

        viewModel.dayData.observe(this){
            val transaction2 = supportFragmentManager.beginTransaction().replace(R.id.framelayout, fragmentArray[viewModel.dayData.value!!-1])
            transaction2.commit()
        }
    }
}



