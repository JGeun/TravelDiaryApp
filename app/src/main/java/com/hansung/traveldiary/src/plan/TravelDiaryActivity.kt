package com.hansung.traveldiary.src.plan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityTravelDiaryBinding
import com.hansung.traveldiary.util.StatusBarUtil

class TravelDiaryActivity : AppCompatActivity() {
    private lateinit var TF: travelMap
    private lateinit var DF: diary
    private lateinit var binding: ActivityTravelDiaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityTravelDiaryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        val transaction = supportFragmentManager.beginTransaction()

        var TF= travelMap()
        var DF= diary()
        replaceView(TF)

        binding.travleTablayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0->{
                        replaceView(TF)
                    }
                    1->{
                        replaceView(DF)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
//
//        val planBottomDialog  = PlanBottomDialog()
//        planBottomDialog.show(supportFragmentManager, planBottomDialog.tag)
    }
    fun replaceView(tab:Fragment){
        var selectedFragment:Fragment?=null
        selectedFragment=tab
        selectedFragment?.let{
            supportFragmentManager.beginTransaction()
                .replace(R.id.tab_fragment,it).commit()
        }
    }

}

