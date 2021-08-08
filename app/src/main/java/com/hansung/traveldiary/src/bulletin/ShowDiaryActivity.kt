package com.hansung.traveldiary.src.bulletin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityShowDiaryBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.src.UserDiaryData
import java.text.SimpleDateFormat
import java.util.*

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
        val isBulletin = intent.getBooleanExtra("isBulletin", false)


        val diary : UserDiaryData

        if(isBulletin){
            diary= MainActivity.bulletinDiaryArray[index].userDiaryData
        }else{
            diary = MainActivity.userDiaryArray[index]
        }

        println("Show index: ${index} day: ${day}")
        binding.sdDate.text = afterDate(diary.baseData.startDate, day)
        binding.sdTitle.text = diary.diaryArray[day].diaryInfo.diaryTitle
        binding.sdContents.text = diary.diaryArray[day].diaryInfo.diaryContents
        println("Show Title: ${diary.diaryArray[day].diaryInfo.diaryTitle}")
        println("Show Contents: ${diary.diaryArray[day].diaryInfo.diaryContents}")
        val default_imgArr:ArrayList<String> = ArrayList<String>()
        default_imgArr.add("https://media.istockphoto.com/photos/suitcase-minimal-travel-concept-with-blue-background-picture-id1130628787?k=6&m=1130628787&s=612x612&w=0&h=Dg-E5XfMagOFGGSA0kHGSZfkgVw_uE9i1ieGk5SDX_8=")
        if(diary.diaryArray[day].diaryInfo.imagePathArray.size!=0) {
            print("이미지가 있어요")
            binding.sdViewPager.adapter =
                ShowDiaryVPAdapter(diary.diaryArray[day].diaryInfo.imagePathArray,false)
        }else{
            println("이미지가 없어요")
            binding.sdViewPager.adapter =
                ShowDiaryVPAdapter(default_imgArr,true)
        }
        binding.sdViewPager.orientation= ViewPager2.ORIENTATION_HORIZONTAL
        binding.sdIndicator.setViewPager(binding.sdViewPager)
        binding.sdIvBack.setOnClickListener {
            finish()
        }
    }

    fun afterDate(date: String, day: Int, pattern: String = "yyyy-MM-dd"): String {
        val format = SimpleDateFormat(pattern, Locale.getDefault())

        val calendar = Calendar.getInstance()
        format.parse(date)?.let { calendar.time = it }
        calendar.add(Calendar.DAY_OF_YEAR, day)

        return format.format(calendar.time)
    }
}