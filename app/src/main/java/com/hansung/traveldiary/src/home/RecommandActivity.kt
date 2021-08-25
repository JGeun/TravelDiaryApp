package com.hansung.traveldiary.src.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityRecommandBinding
import com.hansung.traveldiary.src.home.adapter.RecommandViewPagerAdapter
import com.hansung.traveldiary.util.StatusBarUtil

class RecommandActivity : AppCompatActivity() {
    data class place(
        val placeImage:Int,
        val placeName:String,
        val placeAddress:String,
        val placeURL:String,
        val placeCal:String

    )
    private val popularPlace:ArrayList<place> = ArrayList<place>()
    private lateinit var binding:ActivityRecommandBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityRecommandBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //더미 데이터

        val naturePlace=ArrayList<place>()
        val historyPlace=ArrayList<place>()

        binding.viewpager.adapter= RecommandViewPagerAdapter(this)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.WHITE_STATUS_BAR)
        TabLayoutMediator(binding.tabLayout,binding.viewpager){tab, position->
            when(position){
                0->tab.text="인기"
                1->tab.text="자연"
                2->tab.text="역사"
            }
        }.attach()
        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}