package com.hansung.traveldiary.src.plan

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityTravelPlanBaseBinding
import com.hansung.traveldiary.util.StatusBarUtil
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TravelPlanBaseActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityTravelPlanBaseBinding.inflate(layoutInflater)
    }
    private lateinit var fragmentManager : FragmentManager;
    private lateinit var transaction : FragmentTransaction
    private val scheduleFragment = ScheduleFragment()
    private val travelPlanMapFragment = TravelPlanMapFragment()
    private lateinit var mapDrawable : Drawable
    private lateinit var scheduleDrawable : Drawable

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        private const val LOCATION_PERMISSION_FUN_REQUEST_CODE = 1001
        val naverRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val kakaoRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)

        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()

        mapDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_map_black, null)!!
        scheduleDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_schedule_black, null)!!
        val menu = intent.getStringExtra("menu")
        if(menu == "schedule"){
            binding.planTopMenu.setImageDrawable(mapDrawable)
            transaction.replace(R.id.tp_fragment, scheduleFragment).commitAllowingStateLoss()
        }else{
            binding.planTopMenu.setImageDrawable(scheduleDrawable)
            transaction.replace(R.id.tp_fragment, travelPlanMapFragment).commitAllowingStateLoss()
        }

        binding.planTopMenu.setOnClickListener{
            println("클릭")
            transaction = fragmentManager.beginTransaction()
            if(binding.planTopMenu.drawable == mapDrawable){
//                transaction.remove(scheduleFragment)
                println("Map으로 체인지")
                binding.planTopMenu.setImageDrawable(scheduleDrawable)
                transaction.replace(R.id.tp_fragment, travelPlanMapFragment).commit()
                return@setOnClickListener
            }else{
                println("Schedule로 체인지")
//                transaction.remove(travelPlanMapFragment)
                binding.planTopMenu.setImageDrawable(mapDrawable)
                transaction.replace(R.id.tp_fragment, scheduleFragment).commit()
                return@setOnClickListener
            }
        }
        val title = intent.getStringExtra("title")
        if(title != "")
            binding.planTopTitle.text = title

        binding.planTopBack.setOnClickListener{
            finish()
        }
    }
}
