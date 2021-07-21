package com.hansung.traveldiary.src.plan.add_day

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityPlanAddDayBinding
import com.hansung.traveldiary.src.bulletin.DiarySectionData
import com.hansung.traveldiary.util.StatusBarUtil

class PlanAddDayActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityPlanAddDayBinding.inflate(layoutInflater)
    }
    private var title : String? = null
    private val planDayList = ArrayList<PlanDayInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)

        title = intent.getStringExtra("title")
        if(title == ""){
            binding.addDayTitle.text = "영진이의 부산여행"
        }else{
            binding.addDayTitle.text = title
        }

        if(planDayList.size == 0){
            binding.addDayNoMsg.isVisible = true
            binding.dsRecyclerview.isVisible = false
        }else{
            binding.addDayNoMsg.isVisible = false
            binding.dsRecyclerview.isVisible = true
        }

        binding.addDayFab.setOnClickListener{
            if(planDayList.size == 0){
                binding.addDayNoMsg.isVisible = false
                binding.dsRecyclerview.isVisible = true
            }
            planDayList.add(PlanDayInfo("2021-07-19"))
            binding.dsRecyclerview.adapter!!.notifyDataSetChanged()
        }

        binding.dsIvBack.setOnClickListener{
            finish()
        }

        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter= DayAddAdapter(planDayList)
            layoutManager= LinearLayoutManager(this@PlanAddDayActivity)
        }
    }

    fun getTitleContents() : String?{
        return title
    }
}