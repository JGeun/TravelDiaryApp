package com.hansung.traveldiary.src.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.hansung.traveldiary.databinding.ActivitySplashBinding
import com.hansung.traveldiary.src.MainActivity
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

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.MAIN_STATUS_BAR)
        val runnable : Runnable = Runnable {
            val pref = getSharedPreferences("login", 0)

            if(!pref.getString("login", "").equals("success")){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        handler.postDelayed(runnable,1500)
    }
}