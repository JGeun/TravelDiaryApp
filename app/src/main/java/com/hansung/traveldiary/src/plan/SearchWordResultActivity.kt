package com.hansung.traveldiary.src.plan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hansung.traveldiary.databinding.ActivitySearchWordResultBinding

class SearchWordResultActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivitySearchWordResultBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.srIvBack.setOnClickListener{
            finish()
        }

    }
}