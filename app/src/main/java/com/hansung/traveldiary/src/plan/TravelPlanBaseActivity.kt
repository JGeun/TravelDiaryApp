package com.hansung.traveldiary.src.plan

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityTravelPlanBaseBinding

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        mapDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_map_black, null)!!
        scheduleDrawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_greenbook, null)!!
        binding.planTopMenu.setImageDrawable(mapDrawable)

        fragmentManager = supportFragmentManager
        transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.tp_fragment, scheduleFragment).commitAllowingStateLoss()

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
    }
}
