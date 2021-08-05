package com.hansung.traveldiary.src.diary.write_diary

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityDiaryImageEditBinding

class DiaryImageEditActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDiaryImageEditBinding.inflate(layoutInflater)
    }
    private val diaryImageList = ArrayList<Drawable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initdiaryImageList()

        binding.editImageRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = EditImageAdapter(diaryImageList)
            setHasFixedSize(true)
        }

        binding.editImageRv.isNestedScrollingEnabled = false
    }
    private fun initdiaryImageList(){
        diaryImageList.add(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul, null)!!)
        diaryImageList.add(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_busan, null)!!)
        diaryImageList.add(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_gangneung, null)!!)
        diaryImageList.add(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_seoul_nearby, null)!!)
        diaryImageList.add(ResourcesCompat.getDrawable(resources, R.drawable.ig_home_daejeon, null)!!)
    }
}