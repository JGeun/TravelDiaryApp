package com.hansung.traveldiary.src.plan.plan_day_section

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.hansung.traveldiary.databinding.ItemPlanDaySectionBinding
import com.hansung.traveldiary.src.plan.TestDBActivity
import com.hansung.traveldiary.src.plan.TravelPlanBaseActivity
import com.hansung.traveldiary.src.plan.model.PlanTotalData
import com.hansung.traveldiary.src.plan.model.SharedPlaceViewModel


class PlanDaySectionAdapter(val planTotalData : PlanTotalData): RecyclerView.Adapter<PlanDaySectionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemPlanDaySectionBinding): RecyclerView.ViewHolder(binding.root){
        val dayText = binding.itemAdpDayCount
        val date = binding.itemAdpDate
        val map = binding.itemAdpMap
        val schedule = binding.itemAdpSchedule
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ItemPlanDaySectionBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context= holder.itemView.context
        val dayCountText = "Day - " + (position+1).toString()
        holder.dayText.text = dayCountText
        holder.date.text = planTotalData.dayList[position].date

        val intent = Intent(context, TravelPlanBaseActivity::class.java)
        intent.putExtra("title", (context as PlanDaySectionActivity).getTitleContents())
        holder.map.setOnClickListener{
            intent.putExtra("menu", "map")
            intent.putExtra("index", position)
            (context as PlanDaySectionActivity).startActivity(intent)
        }
        holder.schedule.setOnClickListener{
            intent.putExtra("menu", "schedule")
            intent.putExtra("index", position)
            (context as PlanDaySectionActivity).startActivity(intent)
        }

//        holder.itemView.setOnClickListener{
//
//        }
    }

    override fun getItemCount() = planTotalData.dayList.size
}