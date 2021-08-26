package com.hansung.traveldiary.src.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hansung.traveldiary.databinding.ActivityGimpoAirportTipBinding

class GimpoAirportTipActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityGimpoAirportTipBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}