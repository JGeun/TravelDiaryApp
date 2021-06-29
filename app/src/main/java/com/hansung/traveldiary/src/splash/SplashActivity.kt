package com.hansung.traveldiary.src.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hansung.traveldiary.databinding.ActivitySplashBinding
import com.hansung.traveldiary.src.login.LoginActivity
import com.hansung.traveldiary.util.StatusBarUtil

class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private val binding by lazy{
        ActivitySplashBinding.inflate(layoutInflater)
    };
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        val runnable : Runnable = Runnable {
            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(0, 0);
        }

        handler.postDelayed(runnable,1500)
    }
}