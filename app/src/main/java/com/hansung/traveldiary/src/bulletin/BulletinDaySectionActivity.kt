package com.hansung.traveldiary.src.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityBulletinDaySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.util.StatusBarUtil

class BulletinDaySectionActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityBulletinDaySectionBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)

        println("BulletinDaySectionActivity 들어옴")
        val index = intent.getIntExtra("index", 0)
        println("받은 index: ${index}")

        binding.bdsTitle.text = MainActivity.bulletinDiaryArray[index].userDiaryData.baseData.title
        binding.bdsIvBack.setOnClickListener{
            finish()
        }

        binding.bdsRecyclerview.apply {
            setHasFixedSize(true)
            adapter= BulletinDaySectionAdapter(index, MainActivity.bulletinDiaryArray[index].userDiaryData.diaryArray.size)
            layoutManager=LinearLayoutManager(this@BulletinDaySectionActivity)
        }
    }
}