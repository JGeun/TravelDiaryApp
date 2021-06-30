package com.hansung.traveldiary.src.travel

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hansung.traveldiary.databinding.ActivityDiaryBinding
import com.hansung.traveldiary.databinding.ActivityLoginBinding
import com.hansung.traveldiary.src.travel.adapter.DiaryImageAdapter

class DiaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiaryBinding
    private val diaryImageList = ArrayList<Drawable>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = DiaryImageAdapter(diaryImageList)
        binding.travelRv.adapter = adapter
        binding.travelRv.
    }
}