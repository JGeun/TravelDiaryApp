package com.hansung.traveldiary.src.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityPostBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rv2.setHasFixedSize(true)
        val PostryList=arrayListOf(
                DiaryData(R.drawable.gwangwhamun),
                DiaryData(R.drawable.gwangwhamun),
                DiaryData(R.drawable.gwangwhamun)
        )
        val adapter= DiaryAdapter(PostryList)
        binding.rv2.adapter=adapter
        binding.rv2.layoutManager=LinearLayoutManager(this)
    }
}