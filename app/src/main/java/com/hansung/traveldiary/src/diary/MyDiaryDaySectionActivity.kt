package com.hansung.traveldiary.src.diary

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityMyDiaryDaySectionBinding
import com.hansung.traveldiary.src.MainActivity
import com.hansung.traveldiary.util.StatusBarUtil

class MyDiaryDaySectionActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityMyDiaryDaySectionBinding.inflate(layoutInflater)
    }
    private val viewModel :DiaryDayViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)

        val showDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_find_black, null)
        val writeDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_edit, null)

        binding.dsChangeImage.setImageDrawable(ResourcesCompat.getDrawable(resources, viewModel.imageData.value!!, null))

        binding.dsChangeImage.setOnClickListener{
            viewModel.change()
            binding.dsChangeImage.setImageDrawable(ResourcesCompat.getDrawable(resources, viewModel.imageData.value!!, null))
        }

        binding.dsIvBack.setOnClickListener{
            finish()
            overridePendingTransition(0, 0)
        }

        val index = intent.getIntExtra("index", 0)

        binding.dsTitle.text = MainActivity.myDiaryList[index].diaryData.diaryBaseData.title


        Log.d("에러수정", MainActivity.myDiaryList.size.toString())
        Log.d("에러수정", MainActivity.myDiaryList[0].diaryData.diaryInfoFolder.diaryDayList[0].date)
        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter= MyDiaryDaySectionAdapter(MainActivity.myDiaryList, index, viewModel)
            layoutManager= LinearLayoutManager(this@MyDiaryDaySectionActivity)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(0, 0)
    }
}