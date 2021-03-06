package com.hansung.traveldiary.src.home.weather

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityWeatherBinding
import com.hansung.traveldiary.src.MainActivity
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

//        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.TRANSPARENT_STATUS_BAR)

        window?.apply {
            this.statusBarColor = ResourcesCompat.getColor(resources, R.color.transparent2, null)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        val now = System.currentTimeMillis()
        val date = Date(now)

        val simpleDateFormat = SimpleDateFormat("HH:mm")
        val time = simpleDateFormat.format(date)
        val hour = time.split(":")[0].toInt()

        val day = getDateDay(now)
        binding.weatherDay.text = day
        binding.weatherCurrentTime.text = time
//        if(hour in 6..18){
//            binding.weatherTotalLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.gradation_day, null)
//        }else{
//            binding.weatherTotalLayout.background = ResourcesCompat.getDrawable(resources, R.drawable.gradation_night, null)
//        }
        binding.tvClouds.text = MainActivity.cloudsText

        var weatherImg : Drawable
        if(MainActivity.weatherId.substring(0, 1).equals("2")){
            weatherImg = ResourcesCompat.getDrawable(
                applicationContext.resources,
                R.drawable.ic_new_thunderstorm,
                null
            )!!
        }else if(MainActivity.weatherId.substring(0, 1).equals("3")){
            weatherImg = ResourcesCompat.getDrawable(
                applicationContext.resources,
                R.drawable.ic_new_drizzling,
                null
            )!!
        }else if(MainActivity.weatherId.substring(0, 1).equals("5")){
            weatherImg = ResourcesCompat.getDrawable(
                applicationContext.resources,
                R.drawable.ic_new_rain,
                null
            )!!
        }else if(MainActivity.weatherId.substring(0, 1).equals("6")){
            weatherImg = ResourcesCompat.getDrawable(
                applicationContext.resources,
                R.drawable.ic_new_snow,
                null
            )!!
        }else if(MainActivity.weatherId.equals("800")){
            weatherImg = ResourcesCompat.getDrawable(
                applicationContext.resources,
                R.drawable.ic_new_sunny,
                null
            )!!
        }else if(MainActivity.weatherId.substring(0, 1).equals("8")){
            weatherImg = ResourcesCompat.getDrawable(
                applicationContext.resources,
                R.drawable.ic_new_cloudy,
                null
            )!!
        }else{
            if(MainActivity.weatherId.equals("771") || MainActivity.weatherId.equals("781")){
                weatherImg = ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_new_windy,
                    null
                )!!
            }else if(MainActivity.weatherId.equals("731") || MainActivity.weatherId.equals("751") || MainActivity.weatherId.equals(
                    "761"
                ) || MainActivity.weatherId.equals("762")){
                weatherImg = ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_new_dust,
                    null
                )!!
            }else if(MainActivity.weatherId.substring(0, 1).equals("7")){
                weatherImg = ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_new_fog,
                    null
                )!!
            }else{
                weatherImg = ResourcesCompat.getDrawable(
                    applicationContext.resources,
                    R.drawable.ic_new_sunny,
                    null
                )!!
            }
        }
        Glide.with(this).load(weatherImg).into(binding.ivWeatherCondition)
        binding.tvWeatherCondition.text = MainActivity.weatherMain
        binding.feelsLikeTemperature.text = MainActivity.feelLikeTempText
        binding.tvCurrentTemperature.text = MainActivity.tempText.substring(0,MainActivity.tempText.length-1)
        binding.tvMaximumTemperature.text = MainActivity.maxTempText
        binding.tvMinimumTemperature.text = MainActivity.minTempText
        binding.tvHumidity.text = MainActivity.humidityText
        binding.tvWindSpeed.text = MainActivity.windSpeedText

        binding.weatherRvWeekly.apply{
            setHasFixedSize(false)
            adapter = WeatherAdapter(MainActivity.weeklyList)
            val horizontalManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            layoutManager = horizontalManager
        }

        binding.ivBackbtn.setOnClickListener {
            finish()
        }

    }

    fun getDateDay(now : Long) : String {

        var day = "";
        val dateFormat = SimpleDateFormat("yyyy-MM-dd");

        val nDate: Date = dateFormat.parse(dateFormat.format(Date(now)));

        val cal: Calendar = Calendar.getInstance();
        cal.time = nDate;
        println("cal: " + cal.get(Calendar.DAY_OF_WEEK))
        when (cal.get(Calendar.DAY_OF_WEEK)) {
            1 -> day = "Sun";
            2 -> day = "Mon";
            3 -> day = "Tue";
            4 -> day = "Wed";
            5 -> day = "Thr";
            6 -> day = "Fri";
            7 -> day = "Sat";
        }

        return day;
    }
}