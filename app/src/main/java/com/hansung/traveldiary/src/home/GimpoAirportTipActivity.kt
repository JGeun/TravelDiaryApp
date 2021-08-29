package com.hansung.traveldiary.src.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hansung.traveldiary.databinding.ActivityGimpoAirportTipBinding
import com.hansung.traveldiary.util.StatusBarUtil

class GimpoAirportTipActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityGimpoAirportTipBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}