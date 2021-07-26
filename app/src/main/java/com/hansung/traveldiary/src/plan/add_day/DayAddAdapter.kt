package com.hansung.traveldiary.src.plan.add_day

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemAddDiaryPlanBinding
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import com.hansung.traveldiary.src.plan.model.PlanTotalData


class DayAddAdapter(val planTotalData : PlanTotalData): RecyclerView.Adapter<DayAddAdapter.ViewHolder>() {

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
        holder.date.text = planTotalData.dayList[position].date

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

    override fun getItemCount() = planTotalData.dayList.size
}