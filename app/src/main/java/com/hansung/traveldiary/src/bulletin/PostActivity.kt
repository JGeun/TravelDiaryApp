package com.hansung.traveldiary.src.bulletin

import android.content.Intent
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
        var count=1
        val PostryList=arrayListOf(
                DiaryData(R.drawable.gwangwhamun,count++),
                DiaryData(R.drawable.gwangwhamun,count++),
                DiaryData(R.drawable.gwangwhamun,count++)
        )
        //이미지 뷰 클릭시 이전화면 전환

        val adapter= DiaryAdapter(PostryList)
        binding.rv2.adapter=adapter
        binding.rv2.layoutManager=LinearLayoutManager(this)
    }
}