package com.hansung.traveldiary.src.bulletin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityShowDiaryBinding
import com.hansung.traveldiary.src.MainActivity

class ShowDiaryActivity : AppCompatActivity(){
    private val binding by lazy{
        ActivityShowDiaryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        window?.apply {
            this.statusBarColor = ResourcesCompat.getColor(resources, R.color.transparent, null)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

//        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.transparent_STATUS_BAR)

        val index = intent.getIntExtra("index", 0)
        val day = intent.getIntExtra("day", 0)

        val diaryDayData = MainActivity.allDiaryList[index].diaryData.diaryInfoFolder.diaryDayFolder[day]

        binding.sdDate.text = diaryDayData.date
        binding.sdContents.text = diaryDayData.diaryInfo.diaryContents
        binding.sdTitle.text = diaryDayData.diaryInfo.diaryTitle

        binding.sdViewPager.adapter= ShowDiaryVPAdapter(diaryDayData.diaryInfo.imagePathArray)
        binding.sdViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.sdIndicator.setViewPager(binding.sdViewPager)
        binding.sdIvBack.setOnClickListener {
            finish()
        }
    }
}