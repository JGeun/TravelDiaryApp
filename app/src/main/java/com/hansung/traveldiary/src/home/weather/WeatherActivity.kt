package com.hansung.traveldiary.src.home.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityWeatherBinding
import com.hansung.traveldiary.util.StatusBarUtil
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {
    private val binding : ActivityWeatherBinding by lazy{
        ActivityWeatherBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val now = System.currentTimeMillis()
        val date = Date(now)

        val simpleDateFormat = SimpleDateFormat("HH")
        val nowTime = simpleDateFormat.format(date).toInt()

        window?.apply {
            this.statusBarColor = ResourcesCompat.getColor(resources, R.color.transparent2, null)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
//        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.TRANSPARENT_STATUS_BAR)

//        if(nowTime >=6 && nowTime<=18){
//            binding.weatherTotalLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.gradation_day, null)
//        }else{
//            binding.weatherTotalLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.gradation_night, null)
//        }
//        println(nowTime)
    }
}