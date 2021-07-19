package com.hansung.traveldiary.src.plan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansung.traveldiary.R
import com.hansung.traveldiary.databinding.ActivityPlanAddDayBinding
import com.hansung.traveldiary.src.bulletin.DiarySectionAdapter
import com.hansung.traveldiary.src.bulletin.DiarySectionData
import com.hansung.traveldiary.src.plan.adapter.DayAddAdapter
import com.hansung.traveldiary.util.StatusBarUtil

class PlanAddDayActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityPlanAddDayBinding.inflate(layoutInflater)
    }
    private val postryList = ArrayList<DiarySectionData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        StatusBarUtil.setStatusBarColor(this, StatusBarUtil.StatusBarColorType.DIARY_SECTION_STATUS_BAR)

        if(postryList.size == 0){
            binding.addDayNoMsg.isVisible = true
            binding.dsRecyclerview.isVisible = false
        }else{
            binding.addDayNoMsg.isVisible = false
            binding.dsRecyclerview.isVisible = true
        }

        binding.addDayFab.setOnClickListener{
            if(postryList.size == 0){
                binding.addDayNoMsg.isVisible = false
                binding.dsRecyclerview.isVisible = true
            }
            postryList.add(DiarySectionData(R.drawable.gwangwhamun, "#부산 #해운대"))
            binding.dsRecyclerview.adapter!!.notifyDataSetChanged()
        }
//        =arrayListOf(
//            DiarySectionData(R.drawable.gwangwhamun, "#부산 #해운대"),
//            DiarySectionData(R.drawable.gwangwhamun,"#부산 #해운대"),
//            DiarySectionData(R.drawable.gwangwhamun,"#부산 #해운대")
//        )

        binding.dsIvBack.setOnClickListener{
            finish()
        }

        binding.dsRecyclerview.apply {
            setHasFixedSize(true)
            adapter= DayAddAdapter(postryList)
            layoutManager= LinearLayoutManager(this@PlanAddDayActivity)
        }
    }
}