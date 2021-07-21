package com.hansung.traveldiary.src.plan.add_day

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hansung.traveldiary.databinding.ItemAddDiaryPlanBinding
import com.hansung.traveldiary.databinding.ItemDiarySectionBinding
import com.hansung.traveldiary.src.bulletin.DiarySectionData
import com.hansung.traveldiary.src.plan.TravelPlanActivity
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity

data class PlanDayInfo(var date: String)

class DayAddAdapter(val planDayList : ArrayList<PlanDayInfo>): RecyclerView.Adapter<DayAddAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAddDiaryPlanBinding): RecyclerView.ViewHolder(binding.root){
        val dayText = binding.itemAdpDayCount
        val date = binding.itemAdpDate
        val map = binding.itemAdpMap
        val schedule = binding.itemAdpSchedule
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemAddDiaryPlanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context= holder.itemView.context
        val dayCountText = "Day - " + (position+1).toString()
        holder.dayText.text = dayCountText
        holder.date.text = planDayList[position].date

        val intent = Intent(context, TravelPlanBaseActivity::class.java)
        intent.putExtra("title", (context as PlanAddDayActivity).getTitleContents())
        holder.map.setOnClickListener{
            intent.putExtra("menu", "map")
            (context as PlanAddDayActivity).startActivity(intent)
        }
        holder.schedule.setOnClickListener{
            intent.putExtra("menu", "schedule")
            (context as PlanAddDayActivity).startActivity(intent)
        }

//        holder.itemView.setOnClickListener{
//
//        }
    }

    override fun getItemCount() = planDayList.size
}